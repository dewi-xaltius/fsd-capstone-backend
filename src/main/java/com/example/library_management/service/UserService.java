package com.example.library_management.service;

import com.example.library_management.entity.User;
import com.example.library_management.entity.Member; // <-- ADD THIS IMPORT
import com.example.library_management.repository.UserRepository;
import com.example.library_management.repository.MemberRepository; // <-- ADD THIS IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.library_management.dto.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository; // <-- AUTOWIRE MemberRepository

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // <-- ADD THIS LOGIC FOR MEMBER ROLE
        if ("MEMBER".equalsIgnoreCase(user.getRole())) {
            // Create a new Member profile for this user
            Member newMember = new Member();
            // You might want to pre-fill name/contactInfo from user's username or a registration form
            // For now, let's use username as name and a placeholder for contactInfo
            newMember.setName(user.getUsername()); // Or prompt for actual name in registration
            newMember.setContactInfo(user.getUsername() + "@example.com"); // Placeholder
            // Address can be null initially, registrationDate/membershipExpiryDate are set by Member constructor

            // Set the Member on the User entity
            user.setMember(newMember); // JPA will cascade persist newMember due to CascadeType.ALL
        }
        // -->

        return userRepository.save(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                    .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getRole()))
                    .collect(Collectors.toList());
    }
}