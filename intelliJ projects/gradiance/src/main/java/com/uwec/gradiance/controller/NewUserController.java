package com.uwec.gradiance.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class NewUserController {
    //mappings return files according to thymeleaf template name syntax.
    // will be tested more in depth when thymeleaf files are available
    @GetMapping("/newuser")
    String home(){
        return "newuser";
    }
}