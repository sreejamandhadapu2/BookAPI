package com.bookapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookapi.model.Book;
import com.bookapi.service.BookService;

import jakarta.validation.Valid;

@RestController
public class BookController {
	
	private final BookService bookservice;
	public BookController(BookService bookservice) {
		this.bookservice = bookservice;	}
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id") int id) {
		return bookservice.getBookById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	@GetMapping("/books")
	public List<Book> getAllBooks(){
		 return bookservice.getAll();
	}
	@PostMapping("/books")
	public ResponseEntity<Book> createBook(@Valid @RequestBody Book book){
		int generatedId = bookservice.saveBook(book);
		book.setId(generatedId);
		return ResponseEntity.status(HttpStatus.CREATED).body(book);
	}
	@PutMapping("/books/{id}")
	public ResponseEntity<String> updateBook(@PathVariable("id") int id,@RequestBody Book book){
		book.setId(id);
		boolean updated = bookservice.updateBook(book);
		if(updated) {
			return ResponseEntity.ok("Book updated successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Book not found with ID: " + id);
	}
	@DeleteMapping("/books/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable("id") int id){
		boolean deleted = bookservice.deleteBook(id);
		if (deleted) {
	        return ResponseEntity.ok("Book deleted successfully");
	    }
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body("Book not found with ID: " + id);
	}
	}

