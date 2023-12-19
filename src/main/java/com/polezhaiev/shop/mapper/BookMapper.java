package com.polezhaiev.shop.mapper;

import com.polezhaiev.shop.config.MapperConfig;
import com.polezhaiev.shop.dto.book.BookDto;
import com.polezhaiev.shop.dto.book.BookDtoWithoutCategoryIds;
import com.polezhaiev.shop.dto.book.CreateBookRequestDto;
import com.polezhaiev.shop.model.Book;
import com.polezhaiev.shop.model.Category;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoryIds = book.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.setCategoryIds(categoryIds);
    }

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto dto);

    @AfterMapping
    default void setCategories(@MappingTarget Book book, CreateBookRequestDto requestDto) {
        Set<Category> categories = requestDto.getCategoryIds()
                .stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }

    BookDtoWithoutCategoryIds toDtoWcIds(Book book);

    @Named("bookById")
    default Book bookById(Long id) {
        return Optional.ofNullable(id)
                .map(Book::new)
                .orElse(null);
    }
}
