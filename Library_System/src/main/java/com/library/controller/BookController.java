package com.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.library.entity.Book;
import com.library.service.BookService;
import com.library.util.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;

import java.util.Optional;

/**
 * Controller for managing books.
 */
@Tag(name = "Book Store", description = "APIs for managing Books")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Operation( summary = AppConstants.CREATE_BOOK_SUMMARY, description = AppConstants.CREATE_BOOK_DESC,
    		 responses = { @ApiResponse( responseCode = "201", description = "Book created")})
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        try {
            Book createdBook = bookService.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
        } catch (Exception ex) {
            logger.error("Error creating book: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create book");
        }
    }

    @Operation(summary = AppConstants.GET_BOOK_SUMMARY, description = AppConstants.GET_BOOK_DESC)
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            Optional<Book> book = bookService.getBookById(id);
            if (book.isPresent()) {
                return ResponseEntity.ok(book.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppConstants.BOOK_NOT_FOUND + id);
            }
        } catch (Exception ex) {
            logger.error("Error fetching book with ID {}: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching book");
        }
    }

    @Operation(summary = AppConstants.UPDATE_BOOK_SUMMARY, description = AppConstants.UPDATE_BOOK_DESC)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        try {
            Book updatedBook = bookService.updateBook(id, bookDetails);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException ex) {
            logger.error("Error updating book with ID {}: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppConstants.BOOK_NOT_FOUND + id);
        } catch (Exception ex) {
            logger.error("Error updating book: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update book");
        }
    }

    @Operation(summary = AppConstants.DELETE_BOOK_SUMMARY, description = AppConstants.DELETE_BOOK_DESC)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            logger.error("Error deleting book with ID {}: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppConstants.BOOK_NOT_FOUND + id);
        } catch (Exception ex) {
            logger.error("Error deleting book: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete book");
        }
    }

    @Operation(summary = AppConstants.LIST_BOOKS_SUMMARY, description = AppConstants.LIST_BOOKS_DESC)
    @GetMapping
    public ResponseEntity<?> listAllBooks(
            @RequestParam(defaultValue = "1") int page,   // Accept 1-based from clients
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        try {
            // Convert to 0-based for Spring
            int adjustedPage = (page > 0) ? page - 1 : 0;

            //It creates a Sort object in descending order if sortDir is "desc", otherwise in ascending order.
            Pageable pageable = PageRequest.of(
                    adjustedPage,
                    size,
                    sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
            );

            Page<Book> books = bookService.listAllBooks(pageable);
            return ResponseEntity.ok(books);
        } catch (Exception ex) {
            logger.error("Error fetching books: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving books");
        }
    }

}
