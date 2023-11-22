package com.polezhaiev.shop.service;

import com.polezhaiev.shop.dto.BookDto;
import com.polezhaiev.shop.dto.CreateBookRequestDto;
import com.polezhaiev.shop.model.Book;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    Book createBookDto(CreateBookRequestDto dto);
}
