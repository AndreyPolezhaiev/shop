package com.polezhaiev.shop;

import com.polezhaiev.shop.model.Book;
import com.polezhaiev.shop.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setAuthor("andrey");
            book.setDescription("about life");
            book.setIsbn("342225");
            book.setTitle("biography");
            book.setCoverImage("birds int the sky");
            book.setPrice(BigDecimal.valueOf(133));

            bookService.save(book);

            System.out.println(bookService.findAll());
        };
    }
}
