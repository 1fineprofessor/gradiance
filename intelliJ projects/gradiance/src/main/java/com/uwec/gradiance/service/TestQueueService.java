package com.uwec.gradiance.service;

import com.uwec.gradiance.model.TestQueue;
import com.uwec.gradiance.model.TestQueueNode;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TestQueueService {
    private final TestQueue queue = new TestQueue();
    private final Map<String, TestQueueNode> activeStudents = new ConcurrentHashMap<>();
    private final Map<String, String> questionAssignments = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        clearAll();
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
            questionAssignments.put(student.getEmail(), questionText);
            student.setQuestionText(questionText);
        }
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

    public String getAssignedQuestion(String email) {
        return questionAssignments.get(email);
    }

    public boolean isActive(String email) {
        if (questionAssignments.containsKey(email)) {
            return true;
        }
        return activeStudents.values().stream()
            .anyMatch(n -> n.getEmail().equalsIgnoreCase(email));
    }
}