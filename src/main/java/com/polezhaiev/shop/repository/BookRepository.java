package com.polezhaiev.shop.repository;

import com.polezhaiev.shop.dto.CreateBookRequestDto;
import com.polezhaiev.shop.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE books b "
            + "SET b.title = :#{#requestDto.title}, "
            + "b.author = :#{#requestDto.author}, "
            + "b.isbn = :#{#requestDto.isbn}, "
            + "b.price = :#{#requestDto.price}, "
            + "b.description = :#{#requestDto.description}, "
            + "b.cover_image = :#{#requestDto.coverImage} "
            + "WHERE b.id = :id", nativeQuery = true)
    void updateBookById(Long id, CreateBookRequestDto requestDto);
}
