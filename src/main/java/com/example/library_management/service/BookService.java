package com.example.library_management.service;

import com.example.library_management.entity.Book;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing book-related operations.
 * Defines methods for CRUD operations and business logic for the Book entity.
 */
public interface BookService {

    /**
     * Retrieves all books from the database.
     * @return List of all books
     */
    List<Book> getAllBooks();

    /**
     * Retrieves a book by its ID.
     * @param id The ID of the book to find
     * @return Optional containing the book if found, empty otherwise
     */
    Optional<Book> getBookById(Long id);

    /**
     * Adds a new book to the database.
     * @param book The book to add
     * @return The saved book
     */
    Book addBook(Book book);

    /**
     * Updates an existing book in the database.
     * @param id The ID of the book to update
     * @param bookDetails The updated book details
     * @return The updated book
     */
    Book updateBook(Long id, Book bookDetails);

    /**
     * Deletes a book from the database by its ID.
     * @param id The ID of the book to delete
     */
    void deleteBook(Long id);
}