package com.polezhaiev.shop.dto.order.order;

import com.polezhaiev.shop.model.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderUpdateStatusRequestDto {
    @NotNull
    private Status status;
}
