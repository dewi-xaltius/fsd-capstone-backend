package com.example.library_management.dto;

// This DTO will be used for general user responses (e.g., after registration, or listing users)
public class UserResponse {
    private Long id;
    private String username;
    private String role;

    public UserResponse(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}