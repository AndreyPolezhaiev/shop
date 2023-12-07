package com.polezhaiev.shop.service.category;

import com.polezhaiev.shop.dto.category.CategoryDto;
import com.polezhaiev.shop.dto.category.CreateCategoryRequestDto;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryRequestDto categoryDto);

    CategoryDto update(Long id, CreateCategoryRequestDto categoryDto);

    void deleteById(Long id);
}
