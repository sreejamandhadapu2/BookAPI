package com.bookapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Book {
	private int id;
	
	@NotBlank(message = "Title is required")
	@Size(max = 150, message = "Title must not exceed 150 characters")
	private String title;
	
	@NotBlank(message = "Author is required")
	@Size(max = 100, message = "Author must not exceed 100 characters")
	private String author; 
	
	public Book(){
		
	}
	public Book(int id,String title,String author) {
		this.id = id;
		this.title = title;
		this.author = author;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	@Override
    public String toString() {
        return "Book{id=" + id
                + ", title='" + title + '\''
                + ", author='" + author + '\'' + '}';
    }

}
