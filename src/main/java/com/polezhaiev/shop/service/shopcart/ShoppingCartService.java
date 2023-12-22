package com.polezhaiev.shop.service.shopcart;

import com.polezhaiev.shop.dto.shopcart.CartItemRequestDto;
import com.polezhaiev.shop.dto.shopcart.CartItemUpdateRequestDto;
import com.polezhaiev.shop.dto.shopcart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCart(Long id);

    ShoppingCartResponseDto addBookToShoppingCart(Long id, CartItemRequestDto requestDto);

    ShoppingCartResponseDto updateCartItem(Long id, CartItemUpdateRequestDto requestDto);

    void deleteShoppingCartById(Long id);
}
