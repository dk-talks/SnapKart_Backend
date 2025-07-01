package com.dk.ecommerce.snapkart_orderservice.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
    private String productId;
    private Long userId;
    private int quantity;
}
