package com.example.library_management; // This package should match your main application class's package

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // This annotation loads the full Spring application context for testing
class LibraryManagementApplicationTests {

    @Test // Marks this method as a JUnit 5 test
    void contextLoads() {
        // This test simply checks if the Spring application context loads successfully.
        // If the context fails to load, this test will fail, indicating a configuration issue.
    }

}