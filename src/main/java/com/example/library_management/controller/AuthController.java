package com.example.library_management.controller;

import com.example.library_management.dto.UserLoginRequest; // <-- USE THIS DTO
import com.example.library_management.dto.UserLoginResponse;
import com.example.library_management.service.CustomUserDetailsService; // <-- ADD THIS IMPORT
import com.example.library_management.util.JwtUtil; // <-- ADD THIS IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService; // Autowire CustomUserDetailsService for UserDetails
    
    @Autowired
    private JwtUtil jwtUtil; // Autowire your JwtUtil

    // Remove the inner LoginRequest class from here, as you'll use the DTO

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserLoginRequest authenticationRequest) { // Use UserLoginRequest DTO
        try {
            // Authenticate the user with username and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            // SecurityContextHolder.getContext().setAuthentication(authentication); // This line is not strictly needed for stateless JWT, as the filter will handle it on subsequent requests

            // Load UserDetails to generate token
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            // Generate JWT token
            final String jwt = jwtUtil.generateToken(userDetails);

            // Get user role from UserDetails authorities
            String role = userDetails.getAuthorities().stream()
                    .findFirst() // Assuming a single role per user for simplicity
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .orElse("UNKNOWN");
            
            // If your roles are stored as "ROLE_MEMBER", etc., and you want "MEMBER" for frontend:
            if (role.startsWith("ROLE_")) {
                role = role.substring(5); // Remove "ROLE_" prefix
            }

            // Return the token and user details in the response
            return ResponseEntity.ok(new UserLoginResponse(jwt, userDetails.getUsername(), role));

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            // Catch any other exceptions during token generation or user detail loading
            return new ResponseEntity<>("An error occurred during login: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}