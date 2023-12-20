package com.polezhaiev.shop.service.shopcart.impl;

import com.polezhaiev.shop.dto.shopcart.CartItemRequestDto;
import com.polezhaiev.shop.dto.shopcart.CartItemUpdateRequestDto;
import com.polezhaiev.shop.dto.shopcart.ShoppingCartResponseDto;
import com.polezhaiev.shop.mapper.CartItemMapper;
import com.polezhaiev.shop.mapper.ShoppingCartMapper;
import com.polezhaiev.shop.model.CartItem;
import com.polezhaiev.shop.model.ShoppingCart;
import com.polezhaiev.shop.repository.shopcart.CartItemRepository;
import com.polezhaiev.shop.repository.shopcart.ShoppingCartRepository;
import com.polezhaiev.shop.service.shopcart.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartResponseDto getShoppingCart(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto addBookToShoppingCart(Long id, CartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);
        ShoppingCart saved = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(saved);
    }

    @Override
    public ShoppingCartResponseDto updateCartItem(
            Long id, CartItemUpdateRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.getById(id);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(cartItem.getShoppingCart());
    }

    @Override
    public void deleteShoppingCartById(Long id) {
        cartItemRepository.deleteById(id);
    }
}
