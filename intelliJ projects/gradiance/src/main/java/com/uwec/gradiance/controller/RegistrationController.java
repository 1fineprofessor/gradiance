package com.uwec.gradiance.controller;

import com.uwec.gradiance.model.RegistrationRequest;
import com.uwec.gradiance.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final RegistrationService regService;

    public RegistrationController(RegistrationService regService) {
        this.regService = regService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @ModelAttribute("registrationRequest")
            @Valid RegistrationRequest request,
            BindingResult errors,
            Model model
    ) {
        regService.register(request, errors);

        if (errors.hasErrors()) {
            // errors will be rendered on the form
            return "register";
        }

        // on success, redirect with a flash parameter
        return "redirect:/login?registered";
    }
}
