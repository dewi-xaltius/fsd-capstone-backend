package com.example.library_management.repository;

import com.example.library_management.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Book entity.
 * Provides CRUD operations and custom queries for managing books in the database.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}