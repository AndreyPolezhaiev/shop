package com.polezhaiev.shop.controller.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polezhaiev.shop.dto.book.BookDto;
import com.polezhaiev.shop.dto.book.CreateBookRequestDto;
import com.polezhaiev.shop.model.Book;
import com.polezhaiev.shop.model.Category;
import com.polezhaiev.shop.repository.book.BookRepository;
import com.polezhaiev.shop.repository.category.CategoryRepository;
import java.math.BigDecimal;
import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected CategoryRepository categoryRepository;

    @BeforeAll
    static void setUp (
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext
            ) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/add-one-category-to-database.sql")
            );
        }
    }

    @AfterAll
    static void afterAll(@Autowired DataSource dataSource) {
        tearDown(dataSource);
    }

    @SneakyThrows
    static void tearDown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/delete-category-from-db.sql")
            );
        }
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all valid books")
    @Sql(scripts = "classpath:database/books/add-two-books-to-database.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAll_ValidBooks_ShouldReturnAllValidBooks() throws Exception {
        List<Category> validCategories = categoryRepository.findAll();
        Long categoryId = validCategories.get(0).getId();

        BookDto bookDto1 = new BookDto()
                .setAuthor("author")
                .setIsbn("966-7343-29-4")
                .setCoverImage("image")
                .setTitle("title")
                .setPrice(BigDecimal.valueOf(46))
                .setDescription("description")
                .setCategoryIds(Set.of(categoryId));

        BookDto bookDto2 = new BookDto()
                .setAuthor("author")
                .setIsbn("966-7343-29-4")
                .setCoverImage("image")
                .setTitle("title")
                .setPrice(BigDecimal.valueOf(46))
                .setDescription("description")
                .setCategoryIds(Set.of(categoryId));

        List<BookDto> expected = List.of(bookDto1, bookDto2);

        MvcResult result = mockMvc.perform(
                get("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), BookDto[].class);

        Assertions.assertEquals(expected.size(), actual.length);
        Assertions.assertEquals(expected.get(0).getTitle(), actual[0].getTitle());
        Assertions.assertEquals(expected.get(1).getTitle(), actual[1].getTitle());
        Assertions.assertEquals(expected.get(0).getAuthor(), actual[0].getAuthor());

        Arrays.stream(actual)
                .mapToLong(BookDto::getId)
                .forEach(bookRepository::deleteById);
    }

    @WithMockUser(username = "admin", roles = {"USER"})
    @Test
    @DisplayName("Get book by valid id")
    @Sql(scripts = "classpath:database/books/add-book-for-get-by-id.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getBookById_WithValidId_ShouldReturnValidBook() throws Exception {
        List<Book> validBooks = bookRepository.findAll();
        Long id = validBooks.get(0).getId();
        List<Category> validCategories = categoryRepository.findAll();
        Long categoryId = validCategories.get(0).getId();

        BookDto expected = new BookDto()
                .setId(id)
                .setAuthor("author")
                .setIsbn("966-7343-29-4")
                .setCoverImage("image")
                .setTitle("title")
                .setPrice(BigDecimal.valueOf(46))
                .setDescription("description")
                .setCategoryIds(Set.of(categoryId));

        MvcResult result = mockMvc.perform(
                get("/api/books/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        EqualsBuilder.reflectionEquals(expected, actual, "categoryIds");

        bookRepository.deleteById(actual.getId());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create the book, should return the book")
    public void create_ValidRequestDto_ShouldReturnBook() throws Exception {
        List<Category> validCategories = categoryRepository.findAll();
        Long categoryId = validCategories.get(0).getId();

        CreateBookRequestDto requestDto = new CreateBookRequestDto()
                .setAuthor("Author")
                .setIsbn("966-7343-29-4")
                .setCoverImage("cover image")
                .setTitle("title")
                .setPrice(BigDecimal.ONE)
                .setDescription("description")
                .setCategoryIds(Set.of(categoryId));

        BookDto expected = new BookDto()
                .setAuthor("Author")
                .setIsbn("966-7343-29-4")
                .setCoverImage("cover image")
                .setTitle("title")
                .setPrice(BigDecimal.ONE)
                .setDescription("description")
                .setCategoryIds(Set.of(categoryId));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                post("/api/books")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");

        bookRepository.deleteById(actual.getId());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete the book by id, should return empty content")
    @Sql(scripts = "classpath:database/books/add-book-for-delete.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteBook_ValidId_ShouldReturnNoContent() throws Exception {
        List<Book> validBooks = bookRepository.findAll();
        Long id = validBooks.get(0).getId();

        MvcResult result = mockMvc.perform(
                        delete("/api/books/{id}", id)
                )
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update the book, should return updated book")
    @Sql(scripts = "classpath:database/books/add-book-for-update.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateBook_ValidRequestDto_ShouldReturnUpdatedBook() throws Exception {
        List<Book> validBooks = bookRepository.findAll();
        Long bookId = validBooks.get(0).getId();
        List<Category> validCategories = categoryRepository.findAll();
        Long categoryId = validCategories.get(0).getId();

        CreateBookRequestDto requestDto = new CreateBookRequestDto()
                .setAuthor("updated author")
                .setIsbn("966-7343-29-4")
                .setCoverImage("updated image")
                .setTitle("updated title")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("updated description")
                .setCategoryIds(Set.of(categoryId));

        BookDto expected = new BookDto()
                .setId(bookId)
                .setAuthor("updated author")
                .setIsbn("966-7343-29-4")
                .setCoverImage("updated image")
                .setTitle("updated title")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("updated description")
                .setCategoryIds(Set.of(categoryId));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        put("/api/books/{id}", bookId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        Assertions.assertEquals(expected, actual);

        bookRepository.deleteById(actual.getId());
    }

    @WithMockUser(username = "admin", roles = {"USER"})
    @Test
    @DisplayName("Search books by search parameters, should return valid books")
    @Sql(scripts = "classpath:database/books/add-two-books-to-database.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void searchBooks_BySearchParameters_ShouldReturnAllValidBooks() throws Exception {
        List<Category> validCategories = categoryRepository.findAll();
        Long categoryId = validCategories.get(0).getId();

        BookDto bookDto1 = new BookDto()
                .setAuthor("author")
                .setIsbn("966-7343-29-4")
                .setCoverImage("image")
                .setTitle("title")
                .setPrice(BigDecimal.valueOf(46))
                .setDescription("description")
                .setCategoryIds(Set.of(categoryId));

        BookDto bookDto2 = new BookDto()
                .setAuthor("author")
                .setIsbn("966-7343-29-4")
                .setCoverImage("image")
                .setTitle("title")
                .setPrice(BigDecimal.valueOf(46))
                .setDescription("description")
                .setCategoryIds(Set.of(categoryId));

        List<BookDto> expected = new ArrayList<>();
        expected.add(bookDto1);
        expected.add(bookDto2);

        MvcResult result = mockMvc.perform(
                        get("/api/books/search")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), BookDto[].class);

        Assertions.assertEquals(expected.size(), actual.length);
        EqualsBuilder.reflectionEquals(expected.get(0), actual[0], "id");
        EqualsBuilder.reflectionEquals(expected.get(1), actual[1], "id");

        Arrays.stream(actual)
                .mapToLong(BookDto::getId)
                .forEach(bookRepository::deleteById);
    }
}
