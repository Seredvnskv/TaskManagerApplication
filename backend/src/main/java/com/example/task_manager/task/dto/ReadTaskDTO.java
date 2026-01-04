package com.example.task_manager.task.dto;

import lombok.Value;

import java.time.Instant;
import java.util.List;

@Value
public class ReadTaskDTO {
    String title;
    String description;
    String status;
    String createdBy;
    Instant createdAt;
    Instant updatedAt;
    Instant dueDate;
    List<String> assignedUsers;
}