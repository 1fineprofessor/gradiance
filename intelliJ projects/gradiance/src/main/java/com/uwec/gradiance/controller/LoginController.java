package com.uwec.gradiance.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
    //mappings return files according to thymeleaf template name syntax.
    // will be tested more in depth when thymeleaf files are available
    @GetMapping("/login")
    public String home(Model model){

        return "login";
    }
}