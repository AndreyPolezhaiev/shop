package com.polezhaiev.shop.repository.book;

import com.polezhaiev.shop.model.Book;
import com.polezhaiev.shop.model.Category;
import com.polezhaiev.shop.repository.category.CategoryRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected CategoryRepository categoryRepository;

    @Test
    @DisplayName("""
            Find all books by categories' id,
            should return all valid books 
        """)
    @Sql(scripts = "classpath:database/books/add-one-category-for-repo-level.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllByCategoriesId_WithValidIds_ShouldReturnAllValidBooks() {
        List<Category> validCategories = categoryRepository.findAll();
        Long id = validCategories.get(0).getId();

        Category category = categoryRepository.getById(id);

        Book book = new Book();
        book.setId(id);
        book.setTitle("title");
        book.setCategories(Set.of(category));
        book.setIsbn("3-42-332-2154");
        book.setAuthor("Author");
        book.setCoverImage("Cover image");
        book.setPrice(BigDecimal.ONE);

        bookRepository.save(book);

        List<Book> actual = bookRepository.findAllByCategoriesId(id);

        assertEquals(1, actual.size());

        bookRepository.deleteById(actual.get(0).getId());
        categoryRepository.deleteById(id);
    }
}
