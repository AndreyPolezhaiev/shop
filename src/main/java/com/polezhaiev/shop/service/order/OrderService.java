package com.polezhaiev.shop.service.order;

import com.polezhaiev.shop.dto.order.item.OrderItemResponseDto;
import com.polezhaiev.shop.dto.order.order.OrderMakeRequestDto;
import com.polezhaiev.shop.dto.order.order.OrderResponseDto;
import com.polezhaiev.shop.dto.order.order.OrderUpdateStatusRequestDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(Long userId, OrderMakeRequestDto requestDto);

    List<OrderResponseDto> findAllOrders(Long userId);

    List<OrderItemResponseDto> findAllItemsInOrder(Long orderId);

    OrderItemResponseDto findItemForOrder(Long orderId, Long orderItemId);

    OrderResponseDto updateStatus(Long orderId, OrderUpdateStatusRequestDto requestDto);

    void deleteOrderItemById(Long userId, Long id);
}
