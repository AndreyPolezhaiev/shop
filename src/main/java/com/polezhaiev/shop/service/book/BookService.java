package com.polezhaiev.shop.service.book;

import com.polezhaiev.shop.dto.book.BookDto;
import com.polezhaiev.shop.dto.book.BookDtoWithoutCategoryIds;
import com.polezhaiev.shop.dto.book.BookSearchParametersDto;
import com.polezhaiev.shop.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto requestDto);

    List<BookDto> searchBooks(BookSearchParametersDto searchParameters);

    List<BookDtoWithoutCategoryIds> findAllByCategories_Id(Long categoryId);
}
