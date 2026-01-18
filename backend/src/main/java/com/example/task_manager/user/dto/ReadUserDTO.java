package com.example.task_manager.user.dto;

import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class ReadUserDTO {
    UUID id;
    String username;
    String email;
    String role;
    Instant createdAt;
}
