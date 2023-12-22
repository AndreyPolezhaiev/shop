package com.polezhaiev.shop.repository.shopcart;

import com.polezhaiev.shop.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
