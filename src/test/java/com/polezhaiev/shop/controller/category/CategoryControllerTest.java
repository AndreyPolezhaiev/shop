package com.polezhaiev.shop.controller.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polezhaiev.shop.dto.book.BookDtoWithoutCategoryIds;
import com.polezhaiev.shop.dto.category.CategoryDto;
import com.polezhaiev.shop.dto.category.CreateCategoryRequestDto;
import com.polezhaiev.shop.model.Book;
import com.polezhaiev.shop.model.Category;
import com.polezhaiev.shop.repository.book.BookRepository;
import com.polezhaiev.shop.repository.category.CategoryRepository;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected BookRepository bookRepository;
    private static final Long DB_CATEGORY_ID = 1L;

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
                    new ClassPathResource("database/category/add-two-books-to-db.sql")
            );
        }
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create the category, should return the category")
    public void create_ValidRequestDto_ShouldReturnCategory() throws Exception {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto()
                .setDescription("description")
                .setName("name");

        CategoryDto expected = new CategoryDto();
        expected.setName("name");
        expected.setDescription("description");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/api/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");

        categoryRepository.deleteById(actual.getId());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all valid categories")
    @Sql(scripts = "classpath:database/category/add-two-categories-to-database.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAll_ValidCategories_ShouldReturnAllValidCategories() throws Exception {
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setName("category1");
        categoryDto1.setDescription("description");

        CategoryDto categoryDto2 = new CategoryDto();
        categoryDto2.setName("category2");
        categoryDto2.setDescription("description");

        List<CategoryDto> expected = List.of(categoryDto1, categoryDto2);

        MvcResult result = mockMvc.perform(
                        get("/api/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), CategoryDto[].class);

        Assertions.assertEquals(expected.size(), actual.length);
        Assertions.assertEquals(expected.get(0).getName(), actual[0].getName());
        Assertions.assertEquals(expected.get(1).getName(), actual[1].getName());
        Assertions.assertEquals(expected.get(0).getDescription(), actual[0].getDescription());
        Assertions.assertEquals(expected.get(1).getDescription(), actual[1].getDescription());

        Arrays.stream(actual)
                .mapToLong(CategoryDto::getId)
                .forEach(categoryRepository::deleteById);
    }

    @WithMockUser(username = "admin", roles = {"USER"})
    @Test
    @DisplayName("Get category by valid id")
    @Sql(scripts = "classpath:database/category/add-category-for-get-by-id.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getCategoryById_WithValidId_ShouldReturnValidCategory() throws Exception {
        List<Category> validCategories = categoryRepository.findAll();
        Long id = validCategories.get(0).getId();

        CategoryDto expected = new CategoryDto();
        expected.setName("delete");
        expected.setDescription("description");
        expected.setId(id);

        MvcResult result = mockMvc.perform(
                        get("/api/categories/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        Assertions.assertEquals(expected, actual);

        categoryRepository.deleteById(actual.getId());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update the category, should return updated category")
    @Sql(scripts = "classpath:database/category/add-category-for-update.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateCategory_ValidRequestDto_ShouldReturnUpdatedCategory() throws Exception {
        List<Category> validCategories = categoryRepository.findAll();
        Long id = validCategories.get(0).getId();

        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto()
                .setName("updated")
                .setDescription("description");

        CategoryDto expected = new CategoryDto();
        expected.setId(id);
        expected.setName("updated");
        expected .setDescription("description");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        put("/api/categories/{id}", id)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        Assertions.assertEquals(expected, actual);

        categoryRepository.deleteById(actual.getId());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete the category by id, should return empty content")
    @Sql(scripts = "classpath:database/category/add-category-for-delete.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteCategory_ValidId_ShouldReturnNoContent() throws Exception {
        List<Category> validCategories = categoryRepository.findAll();
        Long id = validCategories.get(0).getId();

        MvcResult result = mockMvc.perform(
                        delete("/api/categories/{id}", id)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all valid books by category id")
    @Sql(scripts =
            "classpath:database/category/add-category-for-get-books-by-category-id.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAllByCategoryIds_ValidBooks_ShouldReturnAllValidBooks() throws Exception {
        List<Category> validCategories = categoryRepository.findAll();
        List<Book> validBooks = bookRepository.findAll();

        Long id = validCategories.get(0).getId();

        validBooks.forEach(b -> b.setCategories(Set.of(validCategories.get(0))));
        validBooks.forEach(b -> bookRepository.save(b));

        BookDtoWithoutCategoryIds bookDto1 = new BookDtoWithoutCategoryIds()
                .setAuthor("author")
                .setIsbn("966-7343-29-4")
                .setCoverImage("image")
                .setTitle("title")
                .setPrice(BigDecimal.valueOf(46))
                .setDescription("description");

        BookDtoWithoutCategoryIds bookDto2 = new BookDtoWithoutCategoryIds()
                .setAuthor("author")
                .setIsbn("966-7343-29-4")
                .setCoverImage("image")
                .setTitle("title")
                .setPrice(BigDecimal.valueOf(46))
                .setDescription("description");

        List<BookDtoWithoutCategoryIds> expected = List.of(bookDto1, bookDto2);

        MvcResult result = mockMvc.perform(
                        get("/api/categories/{id}/books", id)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDtoWithoutCategoryIds[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), BookDtoWithoutCategoryIds[].class);

        Assertions.assertEquals(expected.size(), actual.length);
        EqualsBuilder.reflectionEquals(expected.get(0), actual[0], "id", "categoriesIds");
        EqualsBuilder.reflectionEquals(expected.get(1), actual[1], "id", "categoriesIds");

        Arrays.stream(actual)
                .mapToLong(BookDtoWithoutCategoryIds::getId)
                .forEach(bookRepository::deleteById);

        categoryRepository.deleteById(id);
    }
}
