package com.example.library_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne; // <-- ADD THIS IMPORT
import jakarta.persistence.MapsId; // <-- ADD THIS IMPORT (if using shared primary key strategy)
import jakarta.persistence.JoinColumn; // <-- ADD THIS IMPORT (if using foreign key strategy from Member to User)
import java.time.LocalDate;

/**
 * Represents a member in the library management system.
 * This entity maps to the 'member' table in the database.
 */
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for each member

    @Column(name = "name", nullable = false)
    private String name; // Full name of the member

    @Column(name = "address")
    private String address; // Address of the member

    @Column(name = "contact_info", nullable = false)
    private String contactInfo; // Contact information (e.g., phone number or email)

    @Column(name = "registration_date")
    private LocalDate registrationDate; // Date when the member registered

    @Column(name = "membership_expiry_date")
    private LocalDate membershipExpiryDate; // Date when membership expires (1 year from registration)

    // Optional: Bidirectional relationship back to User
    // Use 'mappedBy' to indicate that the 'member_id' column is on the 'User' side.
    // If you want the Member's ID to be the same as the User's ID, you can use @MapsId
    // For simplicity, let's keep it optional for now, and manage from User side.
    // @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private User user; // The associated User for this Member profile

    // Default constructor (required by JPA)
    public Member() {
        this.registrationDate = LocalDate.now();
        this.membershipExpiryDate = LocalDate.now().plusYears(1);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getMembershipExpiryDate() {
        return membershipExpiryDate;
    }

    public void setMembershipExpiryDate(LocalDate membershipExpiryDate) {
        this.membershipExpiryDate = membershipExpiryDate;
    }

    // If you added the bidirectional user field:
    // public User getUser() { return user; }
    // public void setUser(User user) { this.user = user; }
}