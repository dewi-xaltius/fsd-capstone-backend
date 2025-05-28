package com.example.library_management.service;

import com.example.library_management.entity.User;
import com.example.library_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User loginUser(String username, String password, String role) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password) || !user.getRole().equals(role)) {
            throw new RuntimeException("Invalid username, password, or role");
        }
        return user;
    }
}