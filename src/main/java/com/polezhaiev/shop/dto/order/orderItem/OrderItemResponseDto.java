package com.polezhaiev.shop.dto.order.orderItem;

import java.util.Set;
import lombok.Data;

@Data
public class OrderItemResponseDto {
    private Long id;
    private Long bookId;
    private Integer quantity;
}
