package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private AuthorRepository authorRepository;

    public Book createBook(Book book) {
        Author incomingAuthor = book.getAuthor();

        if (incomingAuthor.getId() != null) {
            Optional<Author> optionalAuthor = authorRepository.findById(incomingAuthor.getId());
            if (optionalAuthor.isPresent()) {
                book.setAuthor(optionalAuthor.get());
            } else {
                // Author ID provided but doesn't exist — remove ID and save new author
                incomingAuthor.setId(null);
                Author savedAuthor = authorRepository.save(incomingAuthor);
                book.setAuthor(savedAuthor);
            }
        } else {
            // No ID given — create a new author
            Author savedAuthor = authorRepository.save(incomingAuthor);
            book.setAuthor(savedAuthor);
        }

        return bookRepository.save(book);
    }


    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(bookDetails.getTitle());
        book.setIsbn(bookDetails.getIsbn());
        book.setPublishedDate(bookDetails.getPublishedDate());
        book.setAuthor(bookDetails.getAuthor());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Page<Book> listAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}
