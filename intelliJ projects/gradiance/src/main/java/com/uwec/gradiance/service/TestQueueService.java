package com.uwec.gradiance.service;


import com.uwec.gradiance.model.TestQueue;
import com.uwec.gradiance.model.TestQueueNode;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TestQueueService {
    private final TestQueue queue = new TestQueue();
    private final Map<String, TestQueueNode> activeStudents = new ConcurrentHashMap<>();
    private final Map<String, List<String>> questionAssignments = new ConcurrentHashMap<>();
    private static final List<String> DEMO_QUESTIONS = List.of(
            "Question 1","Question 2","Question 3","Question 4","Question 5",
            "Question 6","Question 7","Question 8","Question 9","Question 10");

    private final List<SseEmitter> questionEmitters = new CopyOnWriteArrayList<>();
    private volatile String activeQuestion;

    @PostConstruct
    public void init() {
        clearAll();
    }

    public List<String> getAllQuestions() {
        return DEMO_QUESTIONS;
    }

    public List<TestQueueNode> getAllStudents() {
        return queue.getAllStudents();
    }

    public Optional<TestQueueNode> findStudent(String email) {
        return queue.findStudent(email);
    }

    public void appendStudent(TestQueueNode node) {
        queue.appendStudent(node);
    }

    public Optional<TestQueueNode> callNext(String instructorId) {
        Optional<TestQueueNode> next = queue.callStudent();
        next.ifPresent(s -> {
            activeStudents.put(instructorId, s);
            questionAssignments.remove(s.getEmail());
        });
        return next;
    }

    public void assignQuestion(String instructorId, String questionText) {
        TestQueueNode student = activeStudents.get(instructorId);
        if (student != null) {
            questionAssignments.computeIfAbsent(student.getEmail(), k -> new ArrayList<>()).add(questionText);
            student.addQuestion(questionText);
        }
    }

    public void setActiveQuestion(String questionText) {
        this.activeQuestion = questionText;
        List<SseEmitter> deadEmitters = new ArrayList<>();
        for (SseEmitter emitter : questionEmitters) {
            try {
                emitter.send(SseEmitter.event().name("questionUpdated").data(questionText));
            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        }
        questionEmitters.removeAll(deadEmitters);
    }

    public String getActiveQuestion() {
        return activeQuestion;
    }

    public SseEmitter registerQuestionEmitter() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        if (activeQuestion != null) {
            try {
                emitter.send(SseEmitter.event().name("questionUpdated").data(activeQuestion));
            } catch (IOException ignored) {
            }
        }
        emitter.onCompletion(() -> questionEmitters.remove(emitter));
        emitter.onTimeout(() -> questionEmitters.remove(emitter));
        questionEmitters.add(emitter);
        return emitter;
    }


    public void clearAll() {
        queue.clear();
        activeStudents.clear();
        questionAssignments.clear();
    }

    public void clearForUser(String email) {
        activeStudents.entrySet().removeIf(e -> e.getValue().getEmail().equalsIgnoreCase(email));
        questionAssignments.remove(email);
    }

    public List<String> getAssignedQuestions(String email) {
        if (questionAssignments.containsKey(email)) {
            return List.copyOf(questionAssignments.get(email));
        }
        return DEMO_QUESTIONS;
    }

    public boolean isActive(String email) {
        if (questionAssignments.containsKey(email)) {
            return true;
        }
        return activeStudents.values().stream()
            .anyMatch(n -> n.getEmail().equalsIgnoreCase(email));
    }
}