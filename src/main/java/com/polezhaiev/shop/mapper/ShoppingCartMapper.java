package com.polezhaiev.shop.mapper;

import com.polezhaiev.shop.config.MapperConfig;
import com.polezhaiev.shop.dto.shopcart.ShoppingCartResponseDto;
import com.polezhaiev.shop.model.CartItem;
import com.polezhaiev.shop.model.ShoppingCart;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "cartItemsIds", ignore = true)
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

    @AfterMapping
    default void setCartItemsIds(
            @MappingTarget ShoppingCartResponseDto responseDto, ShoppingCart shoppingCart) {
        Set<Long> cartItemsIds = shoppingCart.getCartItems()
                .stream()
                .map(CartItem::getId)
                .collect(Collectors.toSet());
        responseDto.setCartItemsIds(cartItemsIds);
    }
}
