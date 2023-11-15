package com.polezhaiev.shop.repository;

import com.polezhaiev.shop.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
