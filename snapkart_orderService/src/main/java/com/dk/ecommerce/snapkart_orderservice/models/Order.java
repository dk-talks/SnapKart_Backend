package com.dk.ecommerce.snapkart_orderservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String productId;
    private int quantity;
    private double totalPrice;
    private String status; // e.g., "PENDING", "COMPLETED", "CANCELLED"
    private String shippingAddress;
}


