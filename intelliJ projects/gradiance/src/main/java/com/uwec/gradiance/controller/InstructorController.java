package com.uwec.gradiance.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class InstructorController {
    //mappings return files according to thymeleaf template name syntax.
    // will be tested more in depth when thymeleaf files are available
    @GetMapping("/instructor")
    public String home(Model model){
        model.addAttribute("message", "welcome to the instructor page");
        return "instructor";
    }
}