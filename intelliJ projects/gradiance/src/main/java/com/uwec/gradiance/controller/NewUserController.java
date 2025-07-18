package com.uwec.gradiance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.uwec.gradiance.service.UserService;
import com.uwec.gradiance.controller.UserDTO;

@Controller
public class NewUserController {
    //mappings return files according to thymeleaf template name syntax.
    // will be tested more in depth when thymeleaf files are available
    private final UserService userService;
    public NewUserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the registration form for new users.
     *
     * Maps GET requests to /newuser and returns the logical view name "newuser",
     * which Thymeleaf resolves to the template 'new_User.html' in resources/templates/.
     * Adds an empty UserDTO to the model to bind form fields to.
     *
     * @param model the Model object used to pass attributes to the view
     * @return the name of the Thymeleaf template ("newuser")
     */
    @GetMapping("/newuser")
    public String showForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "newuser";
    }

    /**
     * Processes the submitted registration form.
     *
     * Maps POST requests to /newuser. Spring binds form data to a UserDTO object.
     * Invokes the UserService.register(...) method to hash and save credentials,
     * then redirects the user to the 'login' page upon success.
     *
     * @param userDto the UserDTO populated from submitted form data
     * @return a redirect string to navigate to the login page
     */
    @PostMapping("/newuser")
    public String register(@ModelAttribute("userDTO") UserDTO dto) {
        userService.register(dto.getEmail(), dto.getPasswordHash());
        return "redirect:/login";
    }
}