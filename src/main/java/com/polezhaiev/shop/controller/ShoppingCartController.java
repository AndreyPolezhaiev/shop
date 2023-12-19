package com.polezhaiev.shop.controller;

import com.polezhaiev.shop.dto.shopcart.CartItemRequestDto;
import com.polezhaiev.shop.dto.shopcart.CartItemUpdateRequestDto;
import com.polezhaiev.shop.dto.shopcart.ShoppingCartResponseDto;
import com.polezhaiev.shop.model.User;
import com.polezhaiev.shop.service.shopcart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Get a shopping cart",
            description = "Get a shopping cart")
    @GetMapping
    public ShoppingCartResponseDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getShoppingCart(user.getId());
    }

    @Operation(summary = "Add a book to the shopping cart",
            description = "Add a book to the shopping cart")
    @PostMapping
    public ShoppingCartResponseDto addBookToShoppingCart(
            Authentication authentication, @RequestBody CartItemRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addBookToShopCart(user.getId(), requestDto);
    }

    @Operation(summary = "Update quantity of a book",
            description = "Update quantity of a book")
    @PutMapping("/cart-items/{id}")
    public ShoppingCartResponseDto updateQuantityOfABook(
            @PathVariable Long id, @RequestBody CartItemUpdateRequestDto requestDto) {
        return shoppingCartService.updateQuantityOfABook(id, requestDto);
    }

    @Operation(summary = "Delete the book",
            description = "Delete the book")
    @DeleteMapping("/cart-items/{id}")
    public void deleteBook(@PathVariable Long id) {
        shoppingCartService.deleteById(id);
    }
}
