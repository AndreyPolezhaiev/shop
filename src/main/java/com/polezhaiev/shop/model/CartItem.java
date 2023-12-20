package com.polezhaiev.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@SQLDelete(sql = "UPDATE carts_items SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@Entity(name = "carts_items")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "shopping_cart_id",
            nullable = false,
            columnDefinition = "int default 0")
    private ShoppingCart shoppingCart;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "book_id",
            nullable = false,
            columnDefinition = "int default 0")
    private Book book;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted = false;
}
