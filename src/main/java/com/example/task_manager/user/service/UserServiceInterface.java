package com.example.task_manager.user.service;

import com.example.task_manager.user.User;
import com.example.task_manager.user.dto.UpdateUserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServiceInterface {
    User createUser(User user);
    Optional<User> getUserById(UUID id);
    List<User> getUsers();
    User updateUser(User user, UpdateUserDTO updateUserDTO);
    void deleteUser(UUID id);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);
}
