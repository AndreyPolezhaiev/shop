package com.polezhaiev.shop.service.order.impl;

import com.polezhaiev.shop.dto.order.item.OrderItemResponseDto;
import com.polezhaiev.shop.dto.order.order.OrderMakeRequestDto;
import com.polezhaiev.shop.dto.order.order.OrderResponseDto;
import com.polezhaiev.shop.dto.order.order.OrderUpdateStatusRequestDto;
import com.polezhaiev.shop.exception.EntityNotFoundException;
import com.polezhaiev.shop.mapper.OrderItemMapper;
import com.polezhaiev.shop.mapper.OrderMapper;
import com.polezhaiev.shop.model.CartItem;
import com.polezhaiev.shop.model.Order;
import com.polezhaiev.shop.model.OrderItem;
import com.polezhaiev.shop.model.ShoppingCart;
import com.polezhaiev.shop.model.Status;
import com.polezhaiev.shop.repository.order.OrderItemRepository;
import com.polezhaiev.shop.repository.order.OrderRepository;
import com.polezhaiev.shop.repository.shopcart.ShoppingCartRepository;
import com.polezhaiev.shop.service.order.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderResponseDto placeOrder(Long userId, OrderMakeRequestDto requestDto) {
        Order order = new Order();
        int total = 0;

        ShoppingCart shoppingCart = shoppingCartRepository.getById(userId);
        order.setUser(shoppingCart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setStatus(Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(BigDecimal.ONE);
        orderRepository.save(order);

        Set<CartItem> cartItems = shoppingCart.getCartItems();
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem: cartItems) {
            OrderItem tempOrderItem = new OrderItem();
            tempOrderItem.setOrder(order);
            tempOrderItem.setBook(cartItem.getBook());
            tempOrderItem.setPrice(cartItem.getBook().getPrice());
            tempOrderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(tempOrderItem);

            total += tempOrderItem.getQuantity().intValue()
                    * tempOrderItem.getPrice().intValue();

            orderItemRepository.save(tempOrderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotal(BigDecimal.valueOf(total));
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> findAllOrders(Long userId) {
        return orderRepository.findAllByUserId(userId)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemResponseDto> findAllItemsInOrder(Long orderId) {
        return orderRepository.getById(orderId).getOrderItems()
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto findItemForOrder(Long orderId, Long orderItemId) {
        return orderRepository.getById(orderId).getOrderItems()
                .stream()
                .filter(i -> i.getId().equals(orderItemId))
                .map(orderItemMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no item for order by id: " + orderId)
                );
    }

    @Override
    public OrderResponseDto updateStatus(Long orderId, OrderUpdateStatusRequestDto requestDto) {
        Order order = orderRepository.getById(orderId);
        order.setStatus(requestDto.getStatus());
        Order updated = orderRepository.save(order);
        return orderMapper.toDto(updated);
    }

    @Override
    public void deleteOrderItemById(Long id) {
        orderItemRepository.deleteById(id);
    }
}
