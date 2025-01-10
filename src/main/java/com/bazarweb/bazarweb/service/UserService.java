package com.bazarweb.bazarweb.service;

import com.bazarweb.bazarweb.configuration.EncryptionConfiguration;
import com.bazarweb.bazarweb.model.User;
import com.bazarweb.bazarweb.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EncryptionConfiguration encryptionConfiguration; // Внедряем бина из конфигурации

    public UserService(UserRepository userRepository, EncryptionConfiguration encryptionConfiguration) {
        this.userRepository = userRepository;
        this.encryptionConfiguration = encryptionConfiguration;
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public User userCreate(User user){
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        return save(user);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found"));
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void updateProfile(String username, User updatedUser) {
        User existingUser = findByUsername(username);
        existingUser.setEmail(updatedUser.getEmail());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            String encodedPassword = encryptionConfiguration.passwordEncoder().encode(updatedUser.getPassword()); // Используем метод из EncryptionConfiguration
            existingUser.setPassword(encodedPassword);
        }

        userRepository.save(existingUser);
    }

    public void deleteUser(String username) {
        User user = findByUsername(username);
        userRepository.delete(user);
    }

    public User findById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }
}