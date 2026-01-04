package com.example.task_manager.user.controller;

import com.example.task_manager.user.User;
import com.example.task_manager.user.dto.CreateUserDTO;
import com.example.task_manager.user.dto.ReadUserDTO;
import com.example.task_manager.user.dto.UpdateUserDTO;
import com.example.task_manager.user.mapper.UserMapper;
import com.example.task_manager.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReadUserDTO>> getAllUsers() {
        List<User> users = userService.getUsers();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<ReadUserDTO> dtos = userMapper.toDTO(users);
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ReadUserDTO> getUserByUsername(@PathVariable String username) {
        User optionalUser = userService.getUserByUsername(username).orElse(null);

        if (optionalUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ReadUserDTO dto = userMapper.toDTO(optionalUser);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ReadUserDTO> getUserByEmail(@PathVariable String email) {
        User optionalUser = userService.getUserByEmail(email).orElse(null);

        if (optionalUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ReadUserDTO dto = userMapper.toDTO(optionalUser);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<ReadUserDTO> createUser(@RequestBody CreateUserDTO dto) {
        User user = userMapper.toEntity(dto);
        User createdUser = userService.createUser(user);
        ReadUserDTO readUserDTO = userMapper.toDTO(createdUser);
        return new ResponseEntity<>(readUserDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReadUserDTO> updateUser(@PathVariable("id") UUID id, @RequestBody UpdateUserDTO dto) {
        User existingUser = userService.getUserById(id).orElse(null);

        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User updatedUser = userService.updateUser(existingUser, dto);
        ReadUserDTO readUserDTO = userMapper.toDTO(updatedUser);
        return new ResponseEntity<>(readUserDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        User existingUser = userService.getUserById(id).orElse(null);

        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
