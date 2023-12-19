package com.polezhaiev.shop.dto.shopcart;

import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<Long> cartItemsIds;
}
