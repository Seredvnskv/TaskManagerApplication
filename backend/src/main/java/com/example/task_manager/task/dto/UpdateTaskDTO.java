package com.example.task_manager.task.dto;

import com.example.task_manager.user.User;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class UpdateTaskDTO {
    String title;
    String description;
    String status;
    String dueDate;
    List<UUID> assignedUsers;
}
