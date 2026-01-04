package com.example.task_manager.user.dto;

import com.example.task_manager.user.role.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CreateUserDTO {
    @NotBlank
    String username;

    @NotBlank
    String email;

    @NotBlank
    String password;

    @NotNull
    Role role;
}
