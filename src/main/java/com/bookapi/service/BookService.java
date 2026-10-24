package com.bookapi.service;
import java.util.List;


import org.springframework.stereotype.Service;

import com.bookapi.model.*;
import com.bookapi.repository.BookRepository;

import java.util.Optional;

@Service
public class BookService {
	
	private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public Optional<Book> getBookById(int id) {
        return Optional.ofNullable(bookRepository.findById(id));
    }
	public List<Book> getAll(){
		return bookRepository.findAll();
	}
	public int saveBook(Book book) {
		return bookRepository.save(book);
	}
	public boolean updateBook(Book book) {
		return bookRepository.update(book);
	}
	public boolean deleteBook(int id) {
		return bookRepository.deleteById(id);
	}
}
