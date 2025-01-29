package com.bazarweb.bazarweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bazarweb.bazarweb.DTO.UserDTO;
import com.bazarweb.bazarweb.enums.UserRole;
import com.bazarweb.bazarweb.exception.UserNotFoundException;
import com.bazarweb.bazarweb.model.User;
import com.bazarweb.bazarweb.repository.UserRepository;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Получить всех пользователей.
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.isBlocked()))
                .toList();
    }

    /**
     * Получить пользователя по ID.
     */
    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.isBlocked());
    }

    /**
     * Удалить пользователя по ID.
     */
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден");
        }
        userRepository.deleteById(id);
    }

    /**
     * Заблокировать или разблокировать пользователя.
     */
    public UserDTO setUserBlockedStatus(int id, boolean blocked) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
        user.setBlocked(blocked);
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.isBlocked());
    }

    /**
     * Обновить роль пользователя.
     */
    public UserDTO updateUserRole(int id, String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
        user.setRole(UserRole.valueOf(role));
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.isBlocked());
    }
}
