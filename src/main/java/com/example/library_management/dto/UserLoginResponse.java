package com.example.library_management.dto;

public class UserLoginResponse {
    private String jwt;
    private String username;
    private String role;

    public UserLoginResponse(String jwt, String username, String role) {
        this.jwt = jwt;
        this.username = username;
        this.role = role;
    }

    // Getters
    public String getJwt() {
        return jwt;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    // Setters (optional, can be removed if not needed)
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }
}