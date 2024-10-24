package com.example.shop.controller;

import com.example.shop.model.User;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model; // правильный импорт!

import java.util.List;


@Controller
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private UserService userService;

}
