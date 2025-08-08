package com.uwec.gradiance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class StudentInterviewController {

    @GetMapping("/studentinterview")
    public String showInterview() {
        return "studentinterview";
    }
}