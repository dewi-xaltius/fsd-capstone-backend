package com.example.library_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne; // <-- ADD THIS IMPORT
import jakarta.persistence.JoinColumn; // <-- ADD THIS IMPORT
import jakarta.persistence.CascadeType; // <-- ADD THIS IMPORT
import jakarta.persistence.FetchType; // <-- ADD THIS IMPORT

/**
 * Represents a user in the library management system,
 * handling authentication and authorization roles.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password; // Hashed password

    @Column(name = "role", nullable = false)
    private String role; // e.g., "MEMBER", "LIBRARIAN"

    // Optional: One-to-one relationship with Member entity for MEMBER roles
    // This assumes a user with role 'MEMBER' will have an associated Member profile
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // CascadeType.ALL means if User is deleted, Member is too
    @JoinColumn(name = "member_id", referencedColumnName = "id") // Foreign key column in 'users' table
    private Member member; // The associated Member profile for this user

    // Default constructor
    public User() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // New getter and setter for the Member relationship
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}