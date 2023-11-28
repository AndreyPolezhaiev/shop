package com.polezhaiev.shop.service;

import com.polezhaiev.shop.dto.BookDto;
import com.polezhaiev.shop.dto.BookSearchParametersDto;
import com.polezhaiev.shop.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto requestDto);

    List<BookDto> searchBooks(BookSearchParametersDto searchParameters);
}
