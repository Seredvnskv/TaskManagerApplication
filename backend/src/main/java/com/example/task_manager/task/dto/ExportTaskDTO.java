package com.example.task_manager.task.dto;

import com.example.task_manager.task.status.TaskStatus;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Value
public class ExportTaskDTO {
    UUID id;
    String title;
    String description;
    TaskStatus status;
    Instant createdAt;
    Instant updatedAt;
    Instant dueDate;
    UUID createdById;
    String createdByUsername;
    List<UUID> assignedUserIds;
    List<String> assignedUsernames;
}
