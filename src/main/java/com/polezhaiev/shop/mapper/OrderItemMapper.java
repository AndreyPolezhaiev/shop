package com.polezhaiev.shop.mapper;

import com.polezhaiev.shop.config.MapperConfig;
import com.polezhaiev.shop.dto.order.item.OrderItemResponseDto;
import com.polezhaiev.shop.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);
}
