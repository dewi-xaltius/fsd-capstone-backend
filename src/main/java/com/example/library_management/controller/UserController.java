package com.example.library_management.controller;

import com.example.library_management.entity.User;
import com.example.library_management.service.UserService;
import com.example.library_management.dto.UserResponse; // <-- ADD THIS IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody User user) { // <-- CHANGE RETURN TYPE
        User newUser = userService.registerUser(user);
        // Create a UserResponse DTO from the saved User entity
        UserResponse response = new UserResponse(newUser.getId(), newUser.getUsername(), newUser.getRole()); // <-- NEW LINE
        return ResponseEntity.ok(response); // <-- RETURN THE DTO
    }

    // We'll update getAllUsers in a later step
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() { // <-- CHANGE RETURN TYPE HERE
        List<UserResponse> users = userService.getAllUsers(); // <-- CHANGE HERE
        return ResponseEntity.ok(users);
    }
}