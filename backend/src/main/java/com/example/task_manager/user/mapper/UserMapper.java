package com.example.task_manager.user.mapper;

import com.example.task_manager.user.User;
import com.example.task_manager.user.dto.CreateUserDTO;
import com.example.task_manager.user.dto.ReadUserDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public User toEntity(CreateUserDTO createUserDTO) {
        return User.builder()
                .username(createUserDTO.getUsername())
                .email(createUserDTO.getEmail())
                .password(createUserDTO.getPassword())
                .role(createUserDTO.getRole())
                .build();
    }

    public ReadUserDTO toDTO(User user) {
        return new ReadUserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getRole().toString()
        );
    }

    public List<ReadUserDTO> toDTO(List<User> users) {
        return users.stream()
                .map(user -> this.toDTO(user))
                .toList();
    }
}
