package com.polezhaiev.shop.mapper;

import com.polezhaiev.shop.config.MapperConfig;
import com.polezhaiev.shop.dto.shopcart.CartItemRequestDto;
import com.polezhaiev.shop.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {
    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookById")
    @Mapping(target = "shoppingCart", ignore = true)
    CartItem toModel(CartItemRequestDto requestDto);
}
