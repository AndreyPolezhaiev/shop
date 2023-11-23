package com.polezhaiev.shop.repository;

import com.polezhaiev.shop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
