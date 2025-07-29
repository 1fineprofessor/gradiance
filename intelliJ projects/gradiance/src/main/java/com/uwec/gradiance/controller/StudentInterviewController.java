package com.uwec.gradiance.controller;

import com.uwec.gradiance.service.TestQueueService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StudentInterviewController {
    private final TestQueueService queueService;

    public StudentInterviewController(TestQueueService queueService) {
        this.queueService = queueService;
    }
    

    @GetMapping("/studentinterview")
    public String showInterview(Model model, Authentication auth) {
        String email = auth.getName();
        String q = queueService.getAssignedQuestion(email);
        model.addAttribute("questionText", q != null ? q : "Waiting for instructor…");
        return "studentinterview";
    }
}