package com.bookapi;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bookapi.config.AppConfig;
import com.bookapi.service.BookService;

public class MainApp {
	
	public static void main(String args[]) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	
	BookService service = context.getBean(BookService.class);
	service.getBookById(101);
	context.close();

}
}