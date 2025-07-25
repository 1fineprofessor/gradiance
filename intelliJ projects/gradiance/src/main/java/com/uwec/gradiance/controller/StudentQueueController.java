package com.uwec.gradiance.controller;

import com.uwec.gradiance.model.TestQueueNode;
import com.uwec.gradiance.service.TestQueueService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class StudentQueueController {
    private final TestQueueService queueService;

    public StudentQueueController(TestQueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/studentQueue")
    public String home(Model model){
        model.addAttribute("students", queueService.getAllStudents());
        return "studentQueue";
    }

    @PostMapping("/join")
    public String joinQueue(@RequestParam String name,
                            @RequestParam String studentClass,
                            @RequestParam String competency,
                            Authentication auth) {
        String email = auth.getName();
        if (queueService.findStudent(email).isEmpty()) {
            queueService.appendStudent(new TestQueueNode(email, name, studentClass, competency));
        }
        return "redirect:/studentQueue";
    }

    @GetMapping("/studentQueue/check-active")
    @ResponseBody
    public Map<String, Object> checkActive(Authentication auth) {
        String email = auth.getName();
        boolean active = queueService.isActive(email);
        Map<String, Object> resp = new HashMap<>();
        resp.put("active", active);
        return resp;
    }
}