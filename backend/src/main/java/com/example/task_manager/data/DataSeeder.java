package com.example.task_manager.data;

import com.example.task_manager.task.repository.TaskRepository;
import com.example.task_manager.task.status.TaskStatus;
import com.example.task_manager.user.repository.UserRepository;

import com.example.task_manager.user.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.task_manager.task.Task;
import com.example.task_manager.user.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final List<Task> tasks = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    private final Random random = new Random();

    @Autowired
    public DataSeeder(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Seeding data...");
        seedUsers();
        seedTasks();
        System.out.println("Seeding completed.");
    }

    private void seedTasks() {
        int numberOfTasksToSeed = 10;

        for (int i = 1; i <= numberOfTasksToSeed; i++) {
            Task task = Task.builder()
                    .title("Sample Task " + i)
                    .description("This is a description for Sample Task " + i)
                    .status(i % 2 == 0 ? TaskStatus.TODO : TaskStatus.IN_PROGRESS)
                    .dueDate(Instant.now().plusSeconds(86400L * (i + 7))) // i + 7 days from now
                    .createdBy(users.get(random.nextInt(users.size())))
                    .assignedUsers(List.of(users.get(random.nextInt(users.size()))))
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
