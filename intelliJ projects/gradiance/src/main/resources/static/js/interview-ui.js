/*
 * UI bits NOT related to timer math:
 *  - Opens the student popout from the "Interview Popout" button UNDER the End button
 *  - When "Ask" is clicked, POST to /api/active-question so studentInterview.html can render it
 *  - Keeps the visible label of the current question updated for the instructor
 */

(function () {
  // CSRF from hidden form
  const csrfToken = document.querySelector('#askQuestionForm input[name="_csrf"]')?.value || '';

  // Popout (student view) – must be the button under End
  let popoutWin;
  const popoutBtn = document.getElementById('popoutButton');
  popoutBtn?.addEventListener('click', () => {
    popoutWin = window.open('/studentinterview', 'studentinterview');
    if (popoutWin) popoutWin.focus();
  });

  // Toggle panels
  const queueToggle = document.getElementById('queue-toggle');
  const leftPanel = document.getElementById('leftPanel');
  const studentInfoToggle = document.getElementById('studentInfo-toggle');
  const studentInfoPanel = document.getElementById('studentInfoPanel');

  queueToggle?.addEventListener('click', () => leftPanel?.classList.toggle('d-none'));
  studentInfoToggle?.addEventListener('click', () => studentInfoPanel?.classList.toggle('d-none'));

  // Ask buttons → set active question on server (so studentInterview.html populates)
  const currentLabel = document.getElementById('currentQuestionLabel');

  document.querySelectorAll('.ask-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const idStr = btn.getAttribute('data-question-id');
      const id = Number(idStr);
      // Update instructor visible label
      const text = btn.closest('li')?.querySelector('span')?.textContent?.trim() || `Question ${idStr}`;
      if (currentLabel) currentLabel.textContent = `${id}`;

      // Notify backend so student page can pull the active question
      fetch('/api/active-question', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify({ id })
      }).then(res => {
        if (!res.ok) {
          console.error('Failed to set active question', res.status, res.statusText);
          return;
        }
        // If popout exists, give it focus
        if (popoutWin && !popoutWin.closed) popoutWin.focus();
      }).catch(err => console.error('Error setting active question', err));
    });
  });
})();