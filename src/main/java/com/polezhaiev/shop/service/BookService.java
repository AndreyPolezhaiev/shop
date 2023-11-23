package com.polezhaiev.shop.service;

import com.polezhaiev.shop.dto.BookDto;
import com.polezhaiev.shop.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto requestDto);
}
