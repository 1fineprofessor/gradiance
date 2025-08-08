package com.uwec.gradiance.controller;

import com.uwec.gradiance.service.TestQueueService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StudentInterviewRestController {
    private final TestQueueService queueService;

    public StudentInterviewRestController(TestQueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/studentinterview/question")
    public Map<String, List<String>> pollQuestion(Authentication auth) {
        var qList = queueService.getAssignedQuestions(auth.getName());
        return Map.of("questionTexts", qList);
    }
}