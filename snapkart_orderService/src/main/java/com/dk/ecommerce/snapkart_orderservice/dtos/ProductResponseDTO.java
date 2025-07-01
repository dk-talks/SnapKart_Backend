package com.dk.ecommerce.snapkart_orderservice.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {
    private String id;
    private String name;
    private double price;
    private int stockQuantity;
}