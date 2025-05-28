package com.example.library_management.service;

import com.example.library_management.entity.Book;
import com.example.library_management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the BookService interface.
 * Contains the business logic for managing books using the BookRepository.
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository; // Injects the BookRepository to perform database operations

    /**
     * Retrieves all books from the database.
     * @return List of all books
     */
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID.
     * @param id The ID of the book to find
     * @return Optional containing the book if found, empty otherwise
     */
    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Adds a new book to the database.
     * @param book The book to add
     * @return The saved book
     */
    @Override
    public Book addBook(Book book) {
        // Optionally, you can add validation logic here (e.g., check if ISBN is unique)
        return bookRepository.save(book);
    }

    /**
     * Updates an existing book in the database.
     * @param id The ID of the book to update
     * @param bookDetails The updated book details
     * @return The updated book
     */
    @Override
    public Book updateBook(Long id, Book bookDetails) {
        // Find the existing book by ID
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // Update the fields with the new details
        existingBook.setIsbn(bookDetails.getIsbn());
        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setAuthor(bookDetails.getAuthor());
        existingBook.setCategory(bookDetails.getCategory());
        existingBook.setPublicationYear(bookDetails.getPublicationYear());
        existingBook.setCopiesAvailable(bookDetails.getCopiesAvailable());
        existingBook.setStatus(bookDetails.getStatus());

        // Save the updated book back to the database
        return bookRepository.save(existingBook);
    }

    /**
     * Deletes a book from the database by its ID.
     * @param id The ID of the book to delete
     */
    @Override
    public void deleteBook(Long id) {
        // Check if the book exists before deleting
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}