package com.Demo.webAppDemo2.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class LoginController {

    @GetMapping("login")
    public String login(Map<String, Object> model) {
        return "login";
    }

}
