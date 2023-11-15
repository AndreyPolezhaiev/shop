package com.polezhaiev.shop.service;

import com.polezhaiev.shop.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
