package com.polezhaiev.shop.repository.shopcart;

import com.polezhaiev.shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
