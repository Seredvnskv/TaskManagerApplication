package com.example.task_manager.task.dto;

import com.example.task_manager.task.status.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Value
public class CreateTaskDTO {
    @NotNull
    UUID createdByUserId;

    @NotBlank
    String title;

    @NotBlank
    String description;

    TaskStatus status;

    @NotNull
    Instant dueDate;

    List<UUID> assignedUsers;
}
