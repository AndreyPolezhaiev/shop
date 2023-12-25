package com.polezhaiev.shop.controller;

import com.polezhaiev.shop.dto.order.item.OrderItemResponseDto;
import com.polezhaiev.shop.dto.order.order.OrderMakeRequestDto;
import com.polezhaiev.shop.dto.order.order.OrderResponseDto;
import com.polezhaiev.shop.dto.order.order.OrderUpdateStatusRequestDto;
import com.polezhaiev.shop.model.User;
import com.polezhaiev.shop.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Place the order", description = "Place the order")
    @PostMapping
    public OrderResponseDto placeOrder(
            Authentication authentication, @RequestBody OrderMakeRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return orderService.placeOrder(user.getId(), requestDto);
    }

    @Operation(summary = "Find all orders", description = "Find all orders")
    @GetMapping
    public List<OrderResponseDto> findAllOrders(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.findAllOrders(user.getId());
    }

    @Operation(summary = "Find all items in the order",
            description = "Find all items in the order")
    @GetMapping("{orderId}/items")
    public List<OrderItemResponseDto> findAllItemsInOrder(@PathVariable Long orderId) {
        return orderService.findAllItemsInOrder(orderId);
    }

    @Operation(summary = "Find the item in order",
            description = "Find the item in order")
    @GetMapping("{orderId}/items/{orderItemId}")
    public OrderItemResponseDto findItemForOrder(
            Authentication authentication,
            @PathVariable Long orderId,
            @PathVariable Long orderItemId) {
        User user = (User) authentication.getPrincipal();
        return orderService.findItemForOrder(orderId, orderItemId);
    }

    @Operation(summary = "Update the status of the order",
            description = "Update the status of the order")
    @PutMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public OrderResponseDto updateStatus(
            Authentication authentication,
            @PathVariable Long orderId,
            @RequestBody OrderUpdateStatusRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return orderService.updateStatus(orderId, requestDto);
    }

    @Operation(summary = "Delete the item", description = "Delete the item")
    @DeleteMapping("/{id}")
    public void deleteOrderItemById(@PathVariable Long id) {
        orderService.deleteOrderItemById(id);
    }
}
