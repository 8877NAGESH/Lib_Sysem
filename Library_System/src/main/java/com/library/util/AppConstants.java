package com.library.util;

public final class AppConstants {
    // Pagination defaults
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    
    // Response messages
    public static final String SUCCESS = "Success";
    public static final String CREATED = "Created Successfully";
    public static final String UPDATED = "Updated Successfully";
    public static final String DELETED = "Deleted Successfully";
    
 // ===================== BOOK API CONSTANTS =====================
    
    // API Summaries
    public static final String CREATE_BOOK_SUMMARY = "Create a new book";
    public static final String GET_BOOK_SUMMARY = "Get a book by ID";
    public static final String UPDATE_BOOK_SUMMARY = "Update a book";
    public static final String DELETE_BOOK_SUMMARY = "Delete a book";
    public static final String LIST_BOOKS_SUMMARY = "List all books";

    // API Descriptions
    public static final String CREATE_BOOK_DESC = "Adds a new book to the system.";
    public static final String GET_BOOK_DESC = "Fetches a book's details using its unique ID.";
    public static final String UPDATE_BOOK_DESC = "Updates the details of an existing book.";
    public static final String DELETE_BOOK_DESC = "Deletes a book by its ID.";
    public static final String LIST_BOOKS_DESC = "Retrieves a paginated list of all books.";

    // Error Messages
    public static final String BOOK_NOT_FOUND = "Book not found with ID: ";

    // ===================== AUTHOR API CONSTANTS =====================
    
    // API Summaries
    public static final String CREATE_AUTHOR_SUMMARY = "Create a new author";
    public static final String GET_AUTHOR_SUMMARY = "Get an author by ID";
    public static final String UPDATE_AUTHOR_SUMMARY = "Update an author";
    public static final String DELETE_AUTHOR_SUMMARY = "Delete an author";
    public static final String LIST_AUTHORS_SUMMARY = "List all authors";

    // API Descriptions
    public static final String CREATE_AUTHOR_DESC = "Adds a new author to the system.";
    public static final String GET_AUTHOR_DESC = "Fetches an author's details using their unique ID.";
    public static final String UPDATE_AUTHOR_DESC = "Updates the details of an existing author.";
    public static final String DELETE_AUTHOR_DESC = "Deletes an author by their ID.";
    public static final String LIST_AUTHORS_DESC = "Retrieves a paginated list of all authors.";

    // Error Messages
    public static final String AUTHOR_NOT_FOUND = "Author not found with ID: ";
    
    private AppConstants() {
        // Private constructor to prevent instantiation
    }
}