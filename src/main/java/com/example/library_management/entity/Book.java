package com.example.library_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
 * Represents a book in the library management system.
 * This entity maps to the 'book' table in the database.
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for each book

    @Column(name = "isbn", nullable = false, unique = true, length = 13)
    private String isbn; // ISBN number of the book

    @Column(name = "title", nullable = false)
    private String title; // Title of the book

    @Column(name = "author", nullable = false)
    private String author; // Author of the book

    @Column(name = "category")
    private String category; // Category of the book (e.g., Fiction, Non-Fiction)

    @Column(name = "publication_year")
    private Integer publicationYear; // Year the book was published

    @Column(name = "copies_available", nullable = false)
    private Integer copiesAvailable; // Number of available copies

    @Column(name = "status", nullable = false)
    private String status; // Status of the book (e.g., available, borrowed, reserved)

    // Default constructor (required by JPA)
    public Book() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Integer getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(Integer copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}