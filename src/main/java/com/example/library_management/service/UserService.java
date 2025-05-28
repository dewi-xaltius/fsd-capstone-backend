package com.example.library_management.service;

import com.example.library_management.entity.User;
import com.example.library_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder; // <-- ADD THIS IMPORT

import java.util.List;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // You will remove this loginUser method later, but for now, we're only focusing on password hashing
    public User loginUser(String username, String password, String role) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password) || !user.getRole().equals(role)) {
            throw new RuntimeException("Invalid username, password, or role");
        }
        return user;
    }
}