package com.example.library_management.dto;

// This DTO will hold the information we send back to the frontend after login
public class UserLoginResponse {
    private String username;
    private String role; // Frontend expects "MEMBER" or "LIBRARIAN"

    public UserLoginResponse(String username, String role) {
        this.username = username;
        this.role = role;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    // You can add setters if needed, but for a response DTO, getters are often sufficient
}