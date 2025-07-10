package com.uwec.gradiance;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    //mappings return files according to thymeleaf template name syntax.
    // will be tested more in depth when thymeleaf files are available
    @GetMapping("/")
    String home(){
        return "greeting";
    }

    @GetMapping("/instructor")
    String instructor() { return "instructor";}

    @GetMapping("/login")
    String login() { return "login";}

    @GetMapping("/newuser")
    String newuser() { return "newuser";}

    @GetMapping("/studentinterview")
    String studentinterview() { return "studentinterview";}

    @GetMapping("/queue")
    String studentQueue() { return "studentqueue";}

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
