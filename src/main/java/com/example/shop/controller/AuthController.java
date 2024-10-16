package com.example.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // имя файла register.html
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // имя файла login.html
    }
}
