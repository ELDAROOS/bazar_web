package com.bazarweb.bazarweb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bazarweb.bazarweb.model.User;
import com.bazarweb.bazarweb.service.UserService;

import lombok.RequiredArgsConstructor;

import java.security.Principal;


@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getProfile(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<String> updateProfile(@RequestBody User updatedUser, Principal principal) {
        userService.updateProfile(principal.getName(), updatedUser);
        return ResponseEntity.ok("Profile updated successfully");
    }
}
