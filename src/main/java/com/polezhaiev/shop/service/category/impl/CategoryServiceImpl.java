package com.polezhaiev.shop.service.category.impl;

import com.polezhaiev.shop.dto.category.CategoryDto;
import com.polezhaiev.shop.dto.category.CreateCategoryRequestDto;
import com.polezhaiev.shop.exception.EntityNotFoundException;
import com.polezhaiev.shop.mapper.CategoryMapper;
import com.polezhaiev.shop.model.Category;
import com.polezhaiev.shop.repository.category.CategoryRepository;
import com.polezhaiev.shop.service.category.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id: " + id))
        );
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryDto) {
        Category category = categoryMapper.toModel(categoryDto);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toModel(requestDto);
        category.setId(id);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
