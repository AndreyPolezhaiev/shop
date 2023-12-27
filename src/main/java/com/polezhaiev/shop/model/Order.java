package com.polezhaiev.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id",
            nullable = false,
            columnDefinition = "int default 0")
    private User user;

    @Column(nullable = false, name = "status")
    private Status status;

    @Column(nullable = false, name = "total")
    private BigDecimal total;

    @Column(nullable = false, name = "order_date")
    private LocalDateTime orderDate;

    @Column(nullable = false, name = "shipping_address")
    private String shippingAddress;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany
    @JoinTable(name = "orders_orders_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "order_item_id"))
    private Set<OrderItem> orderItems;
}
