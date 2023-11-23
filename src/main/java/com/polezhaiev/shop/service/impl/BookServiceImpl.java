package com.polezhaiev.shop.service.impl;

import com.polezhaiev.shop.dto.BookDto;
import com.polezhaiev.shop.dto.BookSearchParametersDto;
import com.polezhaiev.shop.dto.CreateBookRequestDto;
import com.polezhaiev.shop.exception.EntityNotFoundException;
import com.polezhaiev.shop.mapper.BookMapper;
import com.polezhaiev.shop.model.Book;
import com.polezhaiev.shop.repository.book.BookRepository;
import com.polezhaiev.shop.repository.book.spec.BookSpecificationBuilder;
import com.polezhaiev.shop.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public List<BookDto> findAll() {
        return bookRepository.findAll()
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
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id)
        );
        book.setAuthor(requestDto.getAuthor());
        book.setDescription(requestDto.getDescription());
        book.setIsbn(requestDto.getIsbn());
        book.setTitle(requestDto.getTitle());
        book.setPrice(requestDto.getPrice());
        book.setCoverImage(requestDto.getCoverImage());
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
}
