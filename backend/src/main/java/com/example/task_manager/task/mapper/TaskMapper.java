package com.example.task_manager.task.mapper;

import com.example.task_manager.task.Task;
import com.example.task_manager.task.dto.CreateTaskDTO;
import com.example.task_manager.task.dto.ReadTaskDTO;
import com.example.task_manager.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {
    public Task toEntity(CreateTaskDTO createTaskDTO, User user) {
        return Task.builder()
                .title(createTaskDTO.getTitle())
                .description(createTaskDTO.getDescription())
                .status(createTaskDTO.getStatus())
                .dueDate(createTaskDTO.getDueDate())
                .createdBy(user)
                .build();
    }

    public ReadTaskDTO toDTO(Task task) {
        return new ReadTaskDTO(
                task.getTitle(),
                task.getDescription(),
                task.getStatus().toString(),
                task.getCreatedBy().getUsername(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDueDate(),
                task.getAssignedUsers() != null
                    ? task.getAssignedUsers().stream().map(user -> user.getUsername()).toList()
                    : List.of()
        );
    }

    public List<ReadTaskDTO> toDTO(List<Task> tasks) {
        return tasks.stream().map(task -> this.toDTO(task)).toList();
    }
}
