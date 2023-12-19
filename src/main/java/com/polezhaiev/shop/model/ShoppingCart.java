package com.polezhaiev.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "user_id",
            nullable = false,
            columnDefinition = "int default 0")
    private User user;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany
    @JoinTable(name = "shopping_carts_carts_items",
            joinColumns = @JoinColumn(name = "shopping_cart_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_item_id"))
    private Set<CartItem> cartItems;

    public ShoppingCart() {
    }

    public ShoppingCart(Long id) {
        this.id = id;
    }
}
