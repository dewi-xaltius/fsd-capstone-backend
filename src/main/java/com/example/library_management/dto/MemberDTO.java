// src/main/java/com/example/library_management/dto/MemberDTO.java
package com.example.library_management.dto;

import java.time.LocalDate; // Assuming your dates are LocalDate, adjust if you use java.util.Date

public class MemberDTO {
    private Long id;
    private String name;
    private String address;
    private String contactInfo;
    private LocalDate registrationDate; // Assuming LocalDate
    private LocalDate membershipExpiryDate; // Assuming LocalDate

    // You can add other fields from your Member entity here if needed for the frontend

    public MemberDTO() {
        // Default constructor
    }

    // Constructor to easily map from Member entity
    public MemberDTO(Long id, String name, String address, String contactInfo, LocalDate registrationDate, LocalDate membershipExpiryDate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactInfo = contactInfo;
        this.registrationDate = registrationDate;
        this.membershipExpiryDate = membershipExpiryDate;
    }

    // --- Getters and Setters ---
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
}