package com.polezhaiev.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@SQLDelete(sql = "UPDATE orders_items SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@Entity
@Table(name = "orders_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "order_id",
            nullable = false,
            columnDefinition = "int default 0")
    private Order order;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "book_id",
            nullable = false,
            columnDefinition = "int default 0")
    private Book book;

    @Column(nullable = false, name = "quantity")
    private Integer quantity;

    @Column(nullable = false, name = "price")
    private BigDecimal price;

    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted = false;
}
