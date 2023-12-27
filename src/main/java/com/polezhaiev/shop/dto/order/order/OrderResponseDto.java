package com.polezhaiev.shop.dto.order.order;

import com.polezhaiev.shop.model.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<Long> orderItemsIds;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Status status;
}
