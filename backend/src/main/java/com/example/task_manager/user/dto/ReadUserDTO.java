package com.example.task_manager.user.dto;

import lombok.Value;

@Value
public class ReadUserDTO {
    String username;
    String email;
    String role;
}
