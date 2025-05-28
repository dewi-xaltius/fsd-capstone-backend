package com.example.library_management.service;

import com.example.library_management.entity.User; // Correct import for your User entity
import com.example.library_management.repository.UserRepository; // Correct import for your UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority; // For roles
import java.util.List;

import java.util.Collections; // For creating a single-element list if roles are singular

@Service // Mark this class as a Spring Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Inject your UserRepository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Find the user from your database using the UserRepository
        // As findByUsername returns User (not Optional), handle null case explicitly.
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 2. Convert your User's single 'role' string into Spring Security's GrantedAuthorities
        // Spring Security expects roles to be prefixed with "ROLE_".
        // So, if your database has "ADMIN", it should become "ROLE_ADMIN".
        // We will assume your 'role' column already stores the full role name (e.g., "ROLE_ADMIN", "ROLE_USER").
        // If not, we'll need to prefix it here. Let's assume it already has "ROLE_" for now.
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        // If your 'role' field stores "ADMIN" and you want "ROLE_ADMIN", change the above line to:
        // List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        // Make sure to be consistent with how roles are stored/expected.

        // 3. Build and return Spring Security's UserDetails object
        // The password here is the already BCrypt encoded password from your database.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // This MUST be the BCrypt encoded password
                authorities
        );
    }
}