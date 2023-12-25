package com.polezhaiev.shop.mapper;

import com.polezhaiev.shop.config.MapperConfig;
import com.polezhaiev.shop.dto.order.order.OrderResponseDto;
import com.polezhaiev.shop.model.Order;
import com.polezhaiev.shop.model.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItemsIds", ignore = true)
    OrderResponseDto toDto(Order order);

    @AfterMapping
    default void setOrderItems(@MappingTarget OrderResponseDto responseDto, Order order) {
        Set<Long> orderItemsIds = order.getOrderItems()
                .stream()
                .map(OrderItem::getId)
                .collect(Collectors.toSet());
        responseDto.setOrderItemsIds(orderItemsIds);
    }
}
