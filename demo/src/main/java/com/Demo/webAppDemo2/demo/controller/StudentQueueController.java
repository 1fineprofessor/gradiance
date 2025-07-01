package com.Demo.webAppDemo2.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class StudentQueueController {

    @GetMapping("studentQueue")
    public String studentQueue(Map<String, Object> model) {
        return "studentQueue";
    }
}
