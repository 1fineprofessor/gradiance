package com.uwec.gradiance.controller;

import com.uwec.gradiance.service.TestQueueService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StudentInterviewRestController {
    private final TestQueueService queueService;

    public StudentInterviewRestController(TestQueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/studentinterview/question")
    public Map<String, String> pollQuestion(Authentication auth) {
        String q = queueService.getAssignedQuestion(auth.getName());
        return Map.of("questionText", q == null ? "" : q);
    }
}