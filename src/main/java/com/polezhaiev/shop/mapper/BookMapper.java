package com.polezhaiev.shop.mapper;

import com.polezhaiev.shop.config.MapperConfig;
import com.polezhaiev.shop.dto.BookDto;
import com.polezhaiev.shop.dto.CreateBookRequestDto;
import com.polezhaiev.shop.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto dto);
}
