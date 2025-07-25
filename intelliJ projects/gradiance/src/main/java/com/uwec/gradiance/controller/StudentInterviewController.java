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
    public String home(Model model, Authentication auth){
        String questionText = queueService.getAssignedQuestion(auth.getName());
        model.addAttribute("questionText", questionText);
        return "studentinterview";
    }

    @GetMapping("/studentinterview/question")
    @ResponseBody
    public Map<String, Object> getQuestion(Authentication auth) {
        String questionText = queueService.getAssignedQuestion(auth.getName());
        Map<String, Object> resp = new HashMap<>();
        resp.put("questionText", questionText);
        return resp;
    }

    @GetMapping("/studentInterview/question")
    public Map<String, String> currentQuestion(Authentication auth) {
        String email = auth.getName();
        String q = queueService.getAssignedQuestion(email);
        return Map.of("questionText", q);
    }
}