package com.uwec.gradiance.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    //mappings return files according to thymeleaf template name syntax.
    // will be tested more in depth when thymeleaf files are available
    @GetMapping("/")
    String home(){
        return "greeting";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
