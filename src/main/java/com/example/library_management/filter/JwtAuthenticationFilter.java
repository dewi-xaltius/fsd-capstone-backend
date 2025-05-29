package com.example.library_management.filter;

import com.example.library_management.util.JwtUtil;
import com.example.library_management.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// ADD THIS IMPORT FOR LOGGING
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // ADD THIS LOGGER INSTANCE
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                logger.debug("Extracted username: {} from JWT.", username); // ADDED LOG
            } catch (Exception e) {
                // Log specific JWT parsing/validation errors
                logger.error("Error extracting username or parsing JWT: {}", e.getMessage(), e); // IMPROVED LOG
            }
        } else {
            logger.debug("Authorization header is missing or does not start with Bearer."); // ADDED LOG
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;
            try {
                userDetails = this.customUserDetailsService.loadUserByUsername(username);
                logger.debug("Loaded UserDetails for username: {}", username); // ADDED LOG
            } catch (Exception e) {
                logger.error("Error loading UserDetails for username {}: {}", username, e.getMessage(), e); // ADDED LOG
                filterChain.doFilter(request, response); // Continue filter chain if user not found
                return; // Stop further processing in this filter if userDetails can't be loaded
            }
            
            if (userDetails != null && jwtUtil.validateToken(jwt, userDetails)) { // ADDED NULL CHECK FOR userDetails
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                logger.info("Successfully authenticated user: {}", username); // ADDED LOG
            } else {
                logger.warn("JWT validation failed for user {} or userDetails is null.", username); // ADDED LOG
            }
        } else {
            logger.debug("Username is null or authentication already exists in SecurityContext."); // ADDED LOG
        }
        filterChain.doFilter(request, response);
    }
}