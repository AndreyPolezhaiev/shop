package com.polezhaiev.shop.dto.order.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderMakeRequestDto {
    @NotNull
    private String shippingAddress;
}
