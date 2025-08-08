/* 
 * Instructor Timers & UI Glue (single file)
 * -----------------------------------------
 * - Shows a live timer in #questionTimerDisplay (white text in dark box)
 * - Controls both Session (1 per interview) and Question timers (per Start/End)
 * - Uses performance.now() for elapsedMs; Date.now() for audit
 * - Posts to:
 *     - /api/timers/question/end
 *     - /api/timers/session/end
 *     - /api/active-question  (so student page can render current question)
 * - Handles CSRF via hidden input on the page
 * - Retries failed posts; buffers to localStorage if still failing
 * - Auto-ends timers on beforeunload (best effort keepalive)
 *
 * Assumes these elements exist in instructor.html:
 *   - #startButton (Start)
 *   - #endButton   (End)
 *   - #popoutButton (Interview Popout) -- below End button
 *   - .ask-btn buttons with data-question-id
 *   - #questionTimerDisplay (visible timer for instructor)
 *   - #askQuestionForm with hidden _csrf input
 */

(function() {
  // ---------- Config ----------
  const API = {
    questionEnd: '/api/timers/question/end',
    sessionEnd:  '/api/timers/session/end',
    setActive:   '/api/active-question'
  };
  const VALIDATION_LIMITS = { minMs: 10_000, maxMs: 60 * 60 * 1000 }; // <10s or >1h flagged
  const STORAGE_KEYS = {
    session: 'INSTR_SESSION_STATE',
    question: 'INSTR_QUESTION_STATE',
    failQueue: 'FAILED_TIMER_PAYLOADS'
  };

  // Hard-coded placeholders for now; replace when you inject real values
  const studentId = 'STUDENT-123';
  let currentQuestionId = '1674950';

  // CSRF from hidden form
  const csrfToken = document.querySelector('#askQuestionForm input[name="_csrf"]')?.value || '';

  // ---------- Utilities ----------
  const uuidv4 = () =>
    ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
      (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    );
  const nowPerf = () => performance.now();
  const nowWall = () => Date.now();

  const save = (k, v) => sessionStorage.setItem(k, JSON.stringify(v));
  const load = (k) => JSON.parse(sessionStorage.getItem(k) || 'null');

  const loadFailQueue = () => JSON.parse(localStorage.getItem(STORAGE_KEYS.failQueue) || '[]');
  const saveFailQueue = (arr) => localStorage.setItem(STORAGE_KEYS.failQueue, JSON.stringify(arr));

  const flagsFor = (ms) => ({ lt10s: ms < VALIDATION_LIMITS.minMs, gt1h: ms > VALIDATION_LIMITS.maxMs });

  const fmtMMSS = (ms) => {
    const total = Math.max(0, Math.floor(ms / 1000));
    const m = Math.floor(total / 60);
    const s = total % 60;
    return `${m}:${String(s).padStart(2, '0')}`;
  };

  async function postJson(url, data, { keepalive=false } = {}) {
    return fetch(url, {
      method: 'POST',
      keepalive,
      headers: {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': csrfToken
      },
      body: JSON.stringify(data)
    });
  }
  async function postWithRetry(url, data) {
    const delays = [250, 1000, 3000, 10000];
    for (let i = 0; i <= delays.length; i++) {
      try {
        const res = await postJson(url, data);
        if (res.ok) return res;
      } catch (_) {}
      if (i < delays.length) await new Promise(r => setTimeout(r, delays[i]));
    }
    const q = loadFailQueue();
    q.push({ url, data, enqueuedAt: nowWall() });
    saveFailQueue(q);
    return null;
  }
  async function flushFailQueue() {
    const q = loadFailQueue();
    if (!q.length) return;
    const rest = [];
    for (const item of q) {
      try {
        const res = await postJson(item.url, item.data);
        if (!res.ok) rest.push(item);
      } catch { rest.push(item); }
    }
    saveFailQueue(rest);
  }

  // ---------- State ----------
  let sessionState = load(STORAGE_KEYS.session) || {
    sessionTimerId: null, studentId,
    startedAtPerf: null, startedAtWall: null, running: false
  };
  let questionState = load(STORAGE_KEYS.question) || {
    questionTimerId: null, questionId: currentQuestionId, studentId,
    startedAtPerf: null, startedAtWall: null, running: false
  };

  // ---------- DOM ----------
  const startBtn = document.getElementById('startButton');
  const endBtn   = document.getElementById('endButton');
  const popoutBtn = document.getElementById('popoutButton');
  const timerEl  = document.getElementById('questionTimerDisplay'); // the visible timer

  // Returns elapsed time for session in milliseconds
  function getSessionElapsedMs() {
      if (!sessionStartTime) return 0;
      return Date.now() - sessionStartTime;
  }

  // Returns elapsed time for session in seconds
  function getSessionElapsedSeconds() {
      return Math.floor(getSessionElapsedMs() / 1000);
  }

  // Returns elapsed time for current question in milliseconds
  function getQuestionElapsedMs() {
      if (!questionStartTime) return 0;
      return Date.now() - questionStartTime;
  }

  // Returns elapsed time for current question in seconds
  function getQuestionElapsedSeconds() {
      return Math.floor(getQuestionElapsedMs() / 1000);
  }

  // Popout: must be the button below End
  let popoutWin;
  popoutBtn?.addEventListener('click', () => {
    popoutWin = window.open('/studentinterview', 'studentinterview');
    if (popoutWin) popoutWin.focus();
  });

  // Ticker
  let tickerId = null;
  function startTicker(getStartPerf) {
    stopTicker();
    if (!timerEl) return;
    tickerId = window.setInterval(() => {
      const elapsed = nowPerf() - getStartPerf();
      timerEl.textContent = fmtMMSS(elapsed);
    }, 500);
  }
  function stopTicker() {
    if (tickerId) clearInterval(tickerId);
    tickerId = null;
    if (timerEl) timerEl.textContent = '0:00';
  }

  // ---------- Timer control ----------
  function startSessionIfNeeded() {
    if (!sessionState.running) {
      sessionState = {
        sessionTimerId: uuidv4(),
        studentId,
        startedAtPerf: nowPerf(),
        startedAtWall: nowWall(),
        running: true
      };
      save(STORAGE_KEYS.session, sessionState);
    }
  }
  function startQuestion() {
    if (questionState.running) return; // idempotent
    if (!currentQuestionId) { alert('No question selected.'); return; }
    questionState = {
      questionTimerId: uuidv4(),
      questionId: currentQuestionId,
      studentId,
      startedAtPerf: nowPerf(),
      startedAtWall: nowWall(),
      running: true
    };
    save(STORAGE_KEYS.question, questionState);
    startTicker(() => questionState.startedAtPerf);
  }
  async function endQuestion({ keepalive=false } = {}) {
    if (!questionState.running) return;
    const endedWall = nowWall();
    const elapsedMs = nowPerf() - questionState.startedAtPerf;
    const payload = {
      timerId: questionState.questionTimerId,
      studentId: questionState.studentId,
      questionId: questionState.questionId,
      elapsedMs: Math.max(0, Math.round(elapsedMs)),
      startedAt: questionState.startedAtWall,
      endedAt: endedWall,
      validation: flagsFor(elapsedMs),
      pageContext: 'instructor_v1'
    };
    // Reset local state
    questionState = {
      questionTimerId: null, questionId: currentQuestionId, studentId,
      startedAtPerf: null, startedAtWall: null, running: false
    };
    save(STORAGE_KEYS.question, questionState);
    stopTicker();

    if (keepalive) {
      try { await postJson(API.questionEnd, payload, { keepalive: true }); } catch {}
      return;
    }
    const res = await postWithRetry(API.questionEnd, payload);
    if (!res) console.warn('Question end enqueued for later retry.');
  }
  async function endSession({ keepalive=false } = {}) {
    if (!sessionState.running) return;
    const endedWall = nowWall();
    const elapsedMs = nowPerf() - sessionState.startedAtPerf;
    const payload = {
      timerId: sessionState.sessionTimerId,
      studentId: sessionState.studentId,
      elapsedMs: Math.max(0, Math.round(elapsedMs)),
      startedAt: sessionState.startedAtWall,
      endedAt: endedWall,
      validation: flagsFor(elapsedMs),
      pageContext: 'instructor_v1'
    };
    sessionState = {
      sessionTimerId: null, studentId,
      startedAtPerf: null, startedAtWall: null, running: false
    };
    save(STORAGE_KEYS.session, sessionState);

    if (keepalive) {
      try { await postJson(API.sessionEnd, payload, { keepalive: true }); } catch {}
      return;
    }
    const res = await postWithRetry(API.sessionEnd, payload);
    if (!res) console.warn('Session end enqueued for later retry.');
  }

  // ---------- Wire up buttons ----------
  startBtn?.addEventListener('click', () => {
    startSessionIfNeeded();
    startQuestion();
    startBtn.disabled = true;
    if (endBtn) endBtn.disabled = false;
  });
  endBtn?.addEventListener('click', async () => {
    await endQuestion();
    startBtn.disabled = false;
    endBtn.disabled = true;
  });

  // ---------- Ask buttons: set active question on BACKEND + sync local state ----------
  document.querySelectorAll('.ask-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const idAttr = btn.getAttribute('data-question-id');
      const id = Number(idAttr);
      currentQuestionId = id.toString();

      // Update display label if present
      const label = document.getElementById('currentQuestionLabel');
      if (label) label.textContent = currentQuestionId;

      // Persist question id if timer not running
      if (!questionState.running) {
        questionState.questionId = currentQuestionId;
        save(STORAGE_KEYS.question, questionState);
      }

      // Notify backend so student page can populate
      postJson(API.setActive, { id })
        .then(res => {
          if (!res.ok) {
            console.error('Failed to set active question', res.status, res.statusText);
            return;
          }
          // Focus popout if open
          if (popoutWin && !popoutWin.closed) popoutWin.focus();
        })
        .catch(err => console.error('Error setting active question', err));
    });
  });

  // ---------- Boot ----------
  window.addEventListener('load', () => {
    flushFailQueue();
    if (questionState.running) {
      startTicker(() => questionState.startedAtPerf);
      if (startBtn) startBtn.disabled = true;
      if (endBtn)   endBtn.disabled = false;
    } else {
      stopTicker();
      if (startBtn) startBtn.disabled = false;
      if (endBtn)   endBtn.disabled = true;
    }
  });

  // ---------- Auto-end on unload (best effort) ----------
  window.addEventListener('beforeunload', () => {
    if (questionState.running) endQuestion({ keepalive: true });
    if (sessionState.running)  endSession({ keepalive: true });
  });
})();