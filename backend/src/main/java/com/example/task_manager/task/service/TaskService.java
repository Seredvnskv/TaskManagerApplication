package com.example.task_manager.task.service;

import com.example.task_manager.task.Task;
import com.example.task_manager.task.dto.UpdateTaskDTO;
import com.example.task_manager.task.repository.TaskRepository;
import com.example.task_manager.task.status.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService implements TaskServiceInterface {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    @Override
    public Optional<Task> getTaskById(UUID id) {
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> getTaskByTitle(String title) {
       return taskRepository.findByTitle(title);
    }

    @Override
    public List<Task> getTasksByUserId(UUID id) {
        return taskRepository.findAllByCreatedById(id);
    }

    @Override
    public Task createTask(Task task) {
        if (task.getCreatedBy() == null) {
            throw new IllegalArgumentException("Task must have a creator");
        }
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task, UpdateTaskDTO updateTaskDTO) {
        if (updateTaskDTO.getTitle() != null) {
            task.setTitle(updateTaskDTO.getTitle());
        }
        if (updateTaskDTO.getDescription() != null) {
            task.setDescription(updateTaskDTO.getDescription());
        }
        if (updateTaskDTO.getStatus() != null) {
            task.setStatus(TaskStatus.valueOf(updateTaskDTO.getStatus()));
        }
        if (updateTaskDTO.getDueDate() != null) {
            task.setDueDate(Instant.parse(updateTaskDTO.getDueDate()));
        }

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}
