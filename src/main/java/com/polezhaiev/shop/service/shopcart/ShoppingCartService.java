package com.polezhaiev.shop.service.shopcart;

import com.polezhaiev.shop.dto.shopcart.CartItemRequestDto;
import com.polezhaiev.shop.dto.shopcart.CartItemUpdateRequestDto;
import com.polezhaiev.shop.dto.shopcart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCart(Long id);

    ShoppingCartResponseDto addBookToShopCart(Long id, CartItemRequestDto requestDto);

    ShoppingCartResponseDto updateQuantityOfABook(Long id, CartItemUpdateRequestDto requestDto);

    void deleteById(Long id);
}
