package com.example.library_management.service;

import com.example.library_management.entity.User;
import com.example.library_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder; // <-- ADD THIS IMPORT
import com.example.library_management.dto.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired // <-- ADD THIS LINE to inject PasswordEncoder
    private PasswordEncoder passwordEncoder; // <-- ADD THIS LINE

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        // <-- ADD THESE LINES TO HASH THE PASSWORD BEFORE SAVING
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // -->

        return userRepository.save(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        // Convert list of User entities to list of UserResponse DTOs
        return users.stream()
                    .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getRole()))
                    .collect(Collectors.toList());
    }

    
}