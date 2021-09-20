package com.example.webmvc.registration;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                String err = String.valueOf(error);
                System.out.printf("err: %s", err.replace(";", ";\n"));
            });
            model.addAttribute("user", userDto);
            return "registration";
        }
        try {
            userService.registerNewUserAccount(userDto);
        } catch (DataIntegrityViolationException e) {
            bindingResult.addError(new ObjectError("user", "You already exist in our database."));
            return "registration";
        }
        return "redirect:/";
    }
}
