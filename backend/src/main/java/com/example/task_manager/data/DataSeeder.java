package com.example.task_manager.data;

import com.example.task_manager.task.dto.ExportTaskDTO;
import com.example.task_manager.task.mapper.TaskMapper;
import com.example.task_manager.task.repository.TaskRepository;
import com.example.task_manager.task.status.TaskStatus;
import com.example.task_manager.user.repository.UserRepository;

import com.example.task_manager.user.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.task_manager.task.Task;
import com.example.task_manager.user.User;

import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final FileService fileService;
    private final TaskMapper taskMapper;

    private final List<Task> tasks = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    private final Random random = new Random();

    @Autowired
    public DataSeeder(TaskRepository taskRepository, UserRepository userRepository, FileService fileService, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.taskMapper = taskMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Seeding data...");
        seedUsers();
        seedTasks();
        System.out.println("Seeding completed.");

//        List<ExportTaskDTO> exportDTOs = taskMapper.toExportDTO(tasks);
//        fileService.writeToFile(exportDTOs, Path.of("src/main/resources/exported_tasks.json"));
//
//        List<ExportTaskDTO> importedDTOs = fileService.readFromFile(Path.of("src/main/resources/exported_tasks.json"), List.class);
//        System.out.println("Imported " + importedDTOs.size() + " tasks from JSON file.");
    }

    private void seedTasks() {
        int numberOfTasksToSeed = 10;
        List<User> copy = new ArrayList<>(users);

        for (int i = 1; i <= numberOfTasksToSeed; i++) {
            Collections.shuffle(copy);

            Task task = Task.builder()
                    .title("Sample Task " + i)
                    .description("This is a description for Sample Task " + i)
                    .status(i % 2 == 0 ? TaskStatus.TODO : TaskStatus.IN_PROGRESS)
                    .dueDate(Instant.now().plusSeconds(86400L * (i + 7))) // i + 7 days from now
                    .createdBy(users.get(random.nextInt(users.size())))
                    .assignedUsers(List.of(users.get(random.nextInt(copy.size())), users.get(random.nextInt(copy.size()))))
                    .build();

            tasks.add(task);
        }

        taskRepository.saveAll(tasks);
        System.out.println("Task seed completed.");
    }

    private void seedUsers() {
        int numberOfUsersToSeed = 5;

        for (int i = 1; i <= numberOfUsersToSeed; i++) {
            User user = User.builder()
                    .username("user" + i)
                    .email("user" + i + "@example.com")
                    .password("password" + i)
                    .role(Role.USER)
                    .build();

            users.add(user);
        }

        userRepository.saveAll(users);
        System.out.println("User seed completed.");
    }
}
