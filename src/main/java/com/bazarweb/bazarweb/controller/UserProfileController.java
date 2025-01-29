package com.bazarweb.bazarweb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazarweb.bazarweb.DTO.OrderDTO;
import com.bazarweb.bazarweb.DTO.UserDTO;
import com.bazarweb.bazarweb.enums.UserRole;
import com.bazarweb.bazarweb.model.User;
import com.bazarweb.bazarweb.service.UserService;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        User user = userService.findById(id); // Получение сущности пользователя
        List<OrderDTO> orderDTOs = user.getOrders().stream()
            .map(order -> new OrderDTO(order.getId(), order.getDate(), order.getStatus(), order.getTotal(), order.isExecuted()))
            .collect(Collectors.toList());
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPhone(), user.getAddress(), UserRole.USER, orderDTOs, false);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody User updatedUser, Principal principal) {
        userService.updateProfile(principal.getName(), updatedUser);
        return ResponseEntity.ok("Profile updated successfully");
    }
}
