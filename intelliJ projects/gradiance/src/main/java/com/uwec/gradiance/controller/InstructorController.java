package com.uwec.gradiance.controller;

import com.uwec.gradiance.service.TestQueueService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InstructorController {
    private final TestQueueService queueService;

    public InstructorController(TestQueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/instructor")
    public String home(Model model){
        model.addAttribute("students", queueService.getAllStudents());
        model.addAttribute("questions", queueService.getAllQuestions());
        return "instructor";
    }

    @PostMapping("/instructor/call-next")
    public String callNext(Authentication auth) {
        String instructorId = auth.getName();
        queueService.callNext(instructorId);
        return "redirect:/instructor";
    }

    @PostMapping("/instructor/assign-question")
    public String assignQuestion(@RequestParam String questionText, Authentication auth) {
        queueService.assignQuestion(auth.getName(), questionText);
        return "redirect:/instructor";
    }
}