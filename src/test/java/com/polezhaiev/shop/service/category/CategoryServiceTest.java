package com.polezhaiev.shop.service.category;

import com.polezhaiev.shop.dto.category.CategoryDto;
import com.polezhaiev.shop.dto.category.CreateCategoryRequestDto;
import com.polezhaiev.shop.mapper.CategoryMapper;
import com.polezhaiev.shop.model.Category;
import com.polezhaiev.shop.repository.category.CategoryRepository;
import com.polezhaiev.shop.service.category.impl.CategoryServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    private static final Long ID = 1L;

    @Test
    @DisplayName("""
            Find all categories,
            should return all valid categories
        """)
    public void findAll_ValidCategories_ShouldReturnAllValidCategories() {
        Category category = new Category();
        category.setId(ID);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(ID);

        List<Category> categories = List.of(category);
        List<CategoryDto> expected = List.of(categoryDto);

        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        Mockito.when(categoryMapper.toDto(any())).thenReturn(categoryDto);

        List<CategoryDto> actual = categoryService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find the category by valid id,
            should return valid category
        """)
    public void findById_WithValidId_ShouldReturnValidCategory() {
        Category category = new Category();
        category.setId(ID);

        CategoryDto expected = new CategoryDto();
        expected.setId(ID);

        Mockito.when(categoryRepository.findById(any())).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toDto(any())).thenReturn(expected);

        CategoryDto actual = categoryService.getById(ID);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find the category by invalid id,
            should throw EntityNotFoundException
        """)
    public void findById_WithInvalidId_ShouldThrowEntityNotFoundException() {
        String expected = "Can't find category by id: " + ID;

        Mockito.when(categoryRepository.findById(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> categoryService.getById(ID)
        );

        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Correct saving of the category in database,
            should return saved category
        """)
    public void save_ValidCategory_ShouldReturnSavedCategory() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();

        Category category = new Category();
        category.setId(ID);

        CategoryDto expected = new CategoryDto();
        expected.setId(ID);

        Mockito.when(categoryMapper.toModel(any())).thenReturn(category);
        Mockito.when(categoryMapper.toDto(any())).thenReturn(expected);

        CategoryDto actual = categoryService.save(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
        Update the category by valid id,
        should return updated category
        """)
    public void updateCategoryById_WithValidId_ShouldReturnUpdatedCategory() {
        Category category = new Category();
        category.setId(ID);

        CategoryDto expected = new CategoryDto();
        expected.setId(ID);

        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();

        Mockito.when(categoryMapper.toModel(any())).thenReturn(category);
        Mockito.when(categoryMapper.toDto(any())).thenReturn(expected);
        Mockito.when(categoryRepository.save(any())).thenReturn(category);

        CategoryDto actual = categoryService.update(ID, requestDto);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Delete the category by id
        """)
    public void deleteById_WithValidId_ShouldDeleteCategory() {
        categoryService.deleteById(ID);

        Mockito.verify(categoryRepository, times(1)).deleteById(ID);
    }
}
