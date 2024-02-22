package com.polezhaiev.shop.service.book;

import com.polezhaiev.shop.dto.book.BookDto;
import com.polezhaiev.shop.dto.book.BookDtoWithoutCategoryIds;
import com.polezhaiev.shop.dto.book.BookSearchParametersDto;
import com.polezhaiev.shop.dto.book.CreateBookRequestDto;
import com.polezhaiev.shop.mapper.BookMapper;
import com.polezhaiev.shop.model.Book;
import com.polezhaiev.shop.repository.book.BookRepository;
import com.polezhaiev.shop.repository.book.spec.BookSpecificationBuilder;
import com.polezhaiev.shop.service.book.impl.BookServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;

    private static final Long ID = 1L;

    @Test
    @DisplayName("""
            Correct saving of the book in database,
            should return saved book
        """)
    public void save_ValidBook_ShouldReturnSavedBook() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();

        Book book = new Book();
        book.setId(ID);

        BookDto expected = new BookDto();
        expected.setId(ID);

        Mockito.when(bookMapper.toModel(any())).thenReturn(book);
        Mockito.when(bookMapper.toDto(book)).thenReturn(expected);

        BookDto actual = bookService.save(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find all valid books,
            should return all valid books
        """)
    public void findAll_ValidBooks_ShouldReturnAllValidBooks() {
        Book book = new Book();
        book.setId(ID);

        BookDto bookDto = new BookDto();
        bookDto.setId(ID);

        List<BookDto> expected = List.of(bookDto);
        Page<Book> page = new PageImpl<>(List.of(book));

        Mockito.when(bookRepository.findAll(Pageable.unpaged())).thenReturn(page);
        Mockito.when(bookMapper.toDto(any())).thenReturn(bookDto);

        List<BookDto> actual = bookService.findAll(Pageable.unpaged());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find the book by valid id,
            should return valid book
        """)
    public void findById_WithValidId_ShouldReturnValidBook() {
        Book book = new Book();
        book.setId(ID);

        BookDto expected = new BookDto();
        expected.setId(ID);

        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(expected);

        BookDto actual = bookService.findById(ID);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find the book by invalid id,
            should throw EntityNotFoundException
        """)
    public void findById_WithInvalidId_ShouldThrowEntityNotFoundException() {
        String expected = "Can't find book by id: " + ID;

        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> bookService.findById(ID)
        );

        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Delete the book by id
        """)
    public void deleteById_WithValidId_ShouldDeleteBook() {
        bookService.deleteById(ID);

        Mockito.verify(bookRepository, times(1)).deleteById(ID);
    }

    @Test
    @DisplayName("""
        Update the book by valid id,
        should return updated book
        """)
    public void updateBookById_WithValidId_ShouldReturnUpdatedBook() {
        Book book = new Book();
        book.setId(ID);

        BookDto expected = new BookDto();
        expected.setId(ID);

        CreateBookRequestDto requestDto = new CreateBookRequestDto();

        Mockito.when(bookMapper.toModel(any())).thenReturn(book);
        Mockito.when(bookMapper.toDto(book)).thenReturn(expected);
        Mockito.when(bookRepository.save(any())).thenReturn(book);

        BookDto actual = bookService.updateBookById(ID, requestDto);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Search books by search parameters,
            should return all valid books 
        """)
    public void searchBooks_BySearchParameters_ShouldReturnAllValidBooks() {
        Specification<Book> specification = Mockito.mock(Specification.class);

        Book book = new Book();
        book.setId(ID);

        BookDto bookDto = new BookDto();
        bookDto.setId(ID);

        BookSearchParametersDto searchParameters =
                new BookSearchParametersDto(new String[]{}, new String[]{});

        List<Book> books = List.of(book);
        List<BookDto> expected = List.of(bookDto);

        Mockito.when(bookSpecificationBuilder.build(any())).thenReturn(specification);
        Mockito.when(bookRepository.findAll(specification)).thenReturn(books);
        Mockito.when(bookMapper.toDto(any())).thenReturn(bookDto);

        List<BookDto> actual = bookService.searchBooks(searchParameters);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            find all books by valid categories' id,
            should return all valid books 
        """)
    public void findAllByCategoriesId_WithValidIds_ShouldReturnAllValidBooks() {
        Book book = new Book();
        book.setId(ID);

        BookDtoWithoutCategoryIds bookDto = new BookDtoWithoutCategoryIds();
        bookDto.setId(ID);

        List<Book> books = List.of(book);
        List<BookDtoWithoutCategoryIds> expected = List.of(bookDto);

        Mockito.when(bookRepository.findAllByCategoriesId(any())).thenReturn(books);
        Mockito.when(bookMapper.toDtoWcIds(any())).thenReturn(bookDto);

        List<BookDtoWithoutCategoryIds> actual = bookService.findAllByCategoriesId(ID);

        assertEquals(expected, actual);
    }
}
