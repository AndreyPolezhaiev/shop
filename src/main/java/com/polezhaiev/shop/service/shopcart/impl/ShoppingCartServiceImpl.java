package com.polezhaiev.shop.service.shopcart.impl;

import com.polezhaiev.shop.dto.shopcart.CartItemRequestDto;
import com.polezhaiev.shop.dto.shopcart.CartItemUpdateRequestDto;
import com.polezhaiev.shop.dto.shopcart.ShoppingCartResponseDto;
import com.polezhaiev.shop.mapper.ShoppingCartMapper;
import com.polezhaiev.shop.model.CartItem;
import com.polezhaiev.shop.model.ShoppingCart;
import com.polezhaiev.shop.repository.shopcart.CartItemRepository;
import com.polezhaiev.shop.repository.shopcart.ShoppingCartRepository;
import com.polezhaiev.shop.service.shopcart.ShoppingCartService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartResponseDto getShoppingCart(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        Set<CartItem> notDeletedItems = shoppingCart.getCartItems()
                .stream()
                .filter(i -> !i.isDeleted())
                .collect(Collectors.toSet());
        shoppingCart.setCartItems(notDeletedItems);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto addBookToShopCart(Long id, CartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        CartItem cartItem = shoppingCartMapper.toModel(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);
        ShoppingCart saved = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(saved);
    }

    @Override
    public ShoppingCartResponseDto updateQuantityOfABook(
            Long id, CartItemUpdateRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.getById(id);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(cartItem.getShoppingCart());
    }

    @Override
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }
}
