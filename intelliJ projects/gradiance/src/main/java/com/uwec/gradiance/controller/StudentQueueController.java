package com.uwec.gradiance.controller;

import com.uwec.gradiance.model.TestQueue;
import com.uwec.gradiance.model.TestQueueNode;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentQueueController {
    //mappings return files according to thymeleaf template name syntax.
    // will be tested more in depth when thymeleaf files are available

    private final TestQueue queue = new TestQueue();
    @GetMapping("/studentQueue")
    public String home(Model model){
        model.addAttribute("queue", queue.getAllStudents());
        return "studentQueue";
    }

    @PostMapping("/join")
    public String joinQueue(Authentication auth) {
        String email = auth.getName(); // authenticated user's email
        String name = "Student"; // You can get more info if you store names in UserDetails

        // Only join if not already in queue
        if (queue.findStudent(email).isEmpty()) {
            queue.appendStudent(new TestQueueNode(email, name));
        }

        return "redirect:/studentQueue";
    }
}