package com.polezhaiev.shop.service.impl;

import com.polezhaiev.shop.dto.BookDto;
import com.polezhaiev.shop.dto.CreateBookRequestDto;
import com.polezhaiev.shop.exception.EntityNotFoundException;
import com.polezhaiev.shop.mapper.BookMapper;
import com.polezhaiev.shop.model.Book;
import com.polezhaiev.shop.repository.BookRepository;
import com.polezhaiev.shop.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

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
    public Book createBookDto(CreateBookRequestDto dto) {
        return bookMapper.toModel(dto);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBookById(Long id, CreateBookRequestDto requestDto) {
        bookRepository.updateBookById(id, requestDto);
    }
}
