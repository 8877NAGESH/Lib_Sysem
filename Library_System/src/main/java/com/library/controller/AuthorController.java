package com.library.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.service.AuthorService;
import com.library.service.BookService;
import com.library.util.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller for managing authors.
 */
@Tag(name = "Author Management", description = "APIs for managing authors")
@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorService authorService;
    
    @Autowired
    private BookService bookService;

    @Operation(summary = AppConstants.CREATE_AUTHOR_SUMMARY, description = AppConstants.CREATE_AUTHOR_DESC)
    @PostMapping
    public ResponseEntity<?> createAuthor(@Valid @RequestBody Author author) {
        try {
            Author createdAuthor = authorService.createAuthor(author);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
        } catch (Exception ex) {
            logger.error("Error creating author: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create author");
        }
    }

    @Operation(summary = AppConstants.GET_AUTHOR_SUMMARY, description = AppConstants.GET_AUTHOR_DESC)
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        try {
            Optional<Author> author = authorService.getAuthorById(id);
            if (author.isPresent()) {
                return ResponseEntity.ok(author.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppConstants.AUTHOR_NOT_FOUND + id);
            }
        } catch (Exception ex) {
            logger.error("Error fetching author with ID {}: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching author");
        }
    }

    @Operation(summary = AppConstants.UPDATE_AUTHOR_SUMMARY, description = AppConstants.UPDATE_AUTHOR_DESC)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id,  @Valid @RequestBody Author authorDetails) {
        try {
            Author updatedAuthor = authorService.updateAuthor(id, authorDetails);
            return ResponseEntity.ok(updatedAuthor);
        } catch (RuntimeException ex) {
            logger.error("Error updating author with ID {}: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppConstants.AUTHOR_NOT_FOUND + id);
        } catch (Exception ex) {
            logger.error("Error updating author: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update author");
        }
    }

    @Operation(summary = "Delete an author", description = "Deletes an author by their ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            logger.error("Error deleting author with ID {}: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppConstants.AUTHOR_NOT_FOUND + id);
        } catch (Exception ex) {
            logger.error("Error deleting author: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete author");
        }
    }

//    @Operation(summary = "List all authors", description = "Retrieves a paginated list of all authors.")
//    @GetMapping
//    public ResponseEntity<?> listAllAuthors(Pageable pageable) {
//        try {
//            Page<Author> authors = authorService.listAllAuthors(pageable);
//            return ResponseEntity.ok(authors);
//        } catch (Exception ex) {
//            logger.error("Error fetching authors: {}", ex.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving authors");
//        }
//    }
    
    @Operation(summary = AppConstants.LIST_BOOKS_SUMMARY, description = AppConstants.LIST_BOOKS_DESC)
    @GetMapping
    public ResponseEntity<?> listAllBooks(
            @ParameterObject Pageable pageable,  // Supports pagination & sorting
            @Parameter(description = "Sorting field", example = "title")
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)", example = "asc")
            @RequestParam(required = false, defaultValue = "asc") String sortDir) {
        
        try {
            // Apply sorting dynamically
            Pageable sortedPageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
            );

            Page<Book> books = bookService.listAllBooks(sortedPageable);
            return ResponseEntity.ok(books);
        } catch (Exception ex) {
            logger.error("Error fetching books: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving books");
        }
}
}
