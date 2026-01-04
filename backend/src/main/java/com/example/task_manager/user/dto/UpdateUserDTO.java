package com.example.task_manager.user.dto;

import com.example.task_manager.task.Task;
import lombok.Value;

import java.util.List;

@Value
public class UpdateUserDTO {
    String username;
    String email;
    String role;
}
