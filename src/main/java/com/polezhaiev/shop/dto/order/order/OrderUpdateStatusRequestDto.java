package com.polezhaiev.shop.dto.order.order;

import com.polezhaiev.shop.model.Status;
import lombok.Data;

@Data
public class OrderUpdateStatusRequestDto {
    private Status status;
}
