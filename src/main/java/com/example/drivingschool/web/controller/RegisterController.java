package com.example.drivingschool.web.controller;

import com.example.drivingschool.model.Role;
import com.example.drivingschool.model.exceptions.InvalidArgumentsException;
import com.example.drivingschool.model.exceptions.PasswordsDoNotMatchException;
import com.example.drivingschool.service.AuthService;
import com.example.drivingschool.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AuthService authService;
    private final UserService userService;

    public RegisterController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent","register");
        return "master-template";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String phoneNumber
//                           @RequestParam Role role
    ) {
        try{
            this.userService.register(username, password, repeatedPassword, name, surname, phoneNumber, new ArrayList<>());
//            this.userService.register(username, password, repeatedPassword, name, surname, phoneNumber, role);
            return "redirect:/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}
