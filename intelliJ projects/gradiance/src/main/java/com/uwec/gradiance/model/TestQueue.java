package com.uwec.gradiance.model;
import lombok.Getter;

import java.util.LinkedList;
import java.util.Optional;

public class TestQueue {
    private final LinkedList<TestQueueNode> queueSelf = new LinkedList<>();
    @Getter
    private String ID;

    public TestQueue() {
        this.ID = null;
    }

    // Add student to the back of the queue
    public void appendStudent(TestQueueNode newStudent) {
        queueSelf.addLast(newStudent);
    }

    // Call the next student in line (FIFO)
    public Optional<TestQueueNode> callStudent() {
        if (queueSelf.isEmpty()) return Optional.empty();
        return Optional.of(queueSelf.removeFirst());
    }

    // Peek at the next student without removing
    public Optional<TestQueueNode> peekNext() {
        if (queueSelf.isEmpty()) return Optional.empty();
        return Optional.of(queueSelf.getFirst());
    }

    // Find a student by email
    public Optional<TestQueueNode> findStudent(String email) {
        return queueSelf.stream()
                .filter(q -> q.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return queueSelf.isEmpty();
    }

    // Get the full queue
    public LinkedList<TestQueueNode> getAllStudents() {
        return new LinkedList<>(queueSelf); // return a copy to protect internal state
    }
}

