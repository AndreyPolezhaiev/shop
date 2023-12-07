package com.polezhaiev.shop.service.book.impl;

import com.polezhaiev.shop.dto.book.BookDto;
import com.polezhaiev.shop.dto.book.BookDtoWithoutCategoryIds;
import com.polezhaiev.shop.dto.book.BookSearchParametersDto;
import com.polezhaiev.shop.dto.book.CreateBookRequestDto;
import com.polezhaiev.shop.exception.EntityNotFoundException;
import com.polezhaiev.shop.mapper.BookMapper;
import com.polezhaiev.shop.model.Book;
import com.polezhaiev.shop.repository.book.BookRepository;
import com.polezhaiev.shop.repository.book.spec.BookSpecificationBuilder;
import com.polezhaiev.shop.service.book.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto updateBookById(Long id, CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    public List<BookDto> searchBooks(BookSearchParametersDto searchParameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategories_Id(Long categoryId) {
        return bookRepository.findAllByCategories_Id(categoryId)
                .stream()
                .map(bookMapper::toDtoWcIds)
                .toList();
    }
}
