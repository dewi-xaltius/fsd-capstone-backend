package com.example.library_management.controller;

import com.example.library_management.dto.UserLoginResponse; // <-- ADD THIS IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails; // <-- ADD THIS IMPORT

// Keep this LoginRequest class here or move it to a dto package if you prefer
class LoginRequest {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}


@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) { // Changed return type to ResponseEntity<?>
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // <-- ADD OR MODIFY THESE LINES TO RETURN USER INFO
            // Get the UserDetails object from the authenticated principal
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Extract the role. Assuming a single role like "ROLE_MEMBER" or "ROLE_LIBRARIAN".
            // We need to remove the "ROLE_" prefix for the frontend.
            String role = userDetails.getAuthorities().stream()
                                    .filter(a -> a.getAuthority().startsWith("ROLE_")) // Filter for roles
                                    .map(a -> a.getAuthority().substring(5)) // Remove "ROLE_" prefix
                                    .findFirst() // Get the first role
                                    .orElse("UNKNOWN"); // Default if no role found

            // Create the response DTO
            UserLoginResponse response = new UserLoginResponse(userDetails.getUsername(), role);

            // Return the DTO
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
}