package com.Demo.webAppDemo2.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class StudentInterviewController {

    @GetMapping("studentInterview")
    public String studentInterview(Map<String, Object> model) {
        return "studentInterview";
    }
}
