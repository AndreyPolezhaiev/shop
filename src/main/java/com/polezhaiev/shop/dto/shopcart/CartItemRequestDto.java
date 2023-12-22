package com.polezhaiev.shop.dto.shopcart;

import lombok.Data;

@Data
public class CartItemRequestDto {
    private Long bookId;
    private Integer quantity;
}
