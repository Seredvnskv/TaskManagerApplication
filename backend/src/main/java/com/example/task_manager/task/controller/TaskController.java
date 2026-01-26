package com.example.task_manager.task.controller;

import com.example.task_manager.task.Task;
import com.example.task_manager.task.dto.CreateTaskDTO;
import com.example.task_manager.task.dto.ExportTaskDTO;
import com.example.task_manager.task.dto.ReadTaskDTO;
import com.example.task_manager.task.dto.UpdateTaskDTO;
import com.example.task_manager.task.mapper.TaskMapper;
import com.example.task_manager.task.service.TaskService;
import com.example.task_manager.user.User;
import com.example.task_manager.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.userService = userService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReadTaskDTO>> getTasks() {
        List<Task> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<ReadTaskDTO> dtos = taskMapper.toDTO(tasks);
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadTaskDTO> getTaskById(@PathVariable("id") UUID id) {
        Task optionalTask = taskService.getTaskById(id).orElse(null);

        if (optionalTask == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            ReadTaskDTO dto = taskMapper.toDTO(optionalTask);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<ReadTaskDTO> getTaskByTitle(@PathVariable("title") String title) {
        Task optionalTask = taskService.getTaskByTitle(title).orElse(null);

        if (optionalTask == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ReadTaskDTO dto = taskMapper.toDTO(optionalTask);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ReadTaskDTO>> getTasksByUserId(@PathVariable("id") UUID id) {
        List<Task> tasks = taskService.getTasksByUserId(id);

        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<ReadTaskDTO> dtos = taskMapper.toDTO(tasks);
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }
    }

    @GetMapping("/export")
    public ResponseEntity<List<ExportTaskDTO>> exportTasks() {
        List<Task> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<ExportTaskDTO> dtos = taskMapper.toExportDTO(tasks);
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<ReadTaskDTO> createTask(@Valid @RequestBody CreateTaskDTO dto) {
        User user = userService.getUserById(dto.getCreatedByUserId()).orElse(null);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Task task = taskMapper.toEntity(dto, user);

        if (dto.getAssignedUsers() != null && !dto.getAssignedUsers().isEmpty()) {
            assignUsersToTask(task, dto.getAssignedUsers());
        }

        Task createdTask = taskService.createTask(task);
        ReadTaskDTO readTaskDTO = taskMapper.toDTO(createdTask);
        return new ResponseEntity<>(readTaskDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReadTaskDTO> updateTask(@PathVariable("id") UUID id, @Valid @RequestBody UpdateTaskDTO dto) {
        Task task = taskService.getTaskById(id).orElse(null);

        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (dto.getAssignedUsers() != null && !dto.getAssignedUsers().isEmpty()) {
            assignUsersToTask(task, dto.getAssignedUsers());
        }

        Task updatedTask = taskService.updateTask(task, dto);
        ReadTaskDTO readTaskDTO = taskMapper.toDTO(updatedTask);
        return new ResponseEntity<>(readTaskDTO, HttpStatus.OK);
    }

    @PatchMapping("/title/{title}")
    public ResponseEntity<ReadTaskDTO> updateTask(@PathVariable("title") String title, @Valid @RequestBody UpdateTaskDTO dto) {
        Task task = taskService.getTaskByTitle(title).orElse(null);

        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (dto.getAssignedUsers() != null && !dto.getAssignedUsers().isEmpty()) {
            assignUsersToTask(task, dto.getAssignedUsers());
        }

        Task updatedTask = taskService.updateTask(task, dto);
        ReadTaskDTO readTaskDTO = taskMapper.toDTO(updatedTask);
        return new ResponseEntity<>(readTaskDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") UUID id) {
        Task task = taskService.getTaskById(id).orElse(null);

        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            taskService.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private void assignUsersToTask(Task task, List<UUID> userIds) {
        List<User> users = userIds.stream()
                .map(uuid -> userService.getUserById(uuid).orElse(null))
                .filter(u -> u != null)
                .toList();
        task.setAssignedUsers(users);
    }
}
