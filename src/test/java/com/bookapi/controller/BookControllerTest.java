
package com.bookapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bookapi.model.Book;
import com.bookapi.service.BookService;

public class BookControllerTest {

    @Test
    void getBookReturnsBookFromService() {

        BookService mockService = mock(BookService.class);

        Book expectedBook =
                new Book(101, "Effective Java", "Joshua Bloch");

        when(mockService.getBookById(101))
                .thenReturn(Optional.of(expectedBook));

        BookController controller = new BookController(mockService);

        ResponseEntity<Book> response = controller.getBook(101);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Book actualBook = response.getBody();

        assertNotNull(actualBook);
        assertEquals(101, actualBook.getId());
        assertEquals("Effective Java", actualBook.getTitle());
        assertEquals("Joshua Bloch", actualBook.getAuthor());
    }

    @Test
    void getBookReturns404WhenBookDoesNotExist() {

        BookService mockService = mock(BookService.class);

        when(mockService.getBookById(9999))
                .thenReturn(Optional.empty());

        BookController controller = new BookController(mockService);

        ResponseEntity<Book> response = controller.getBook(9999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
