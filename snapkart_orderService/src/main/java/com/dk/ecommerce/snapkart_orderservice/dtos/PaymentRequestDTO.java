package com.dk.ecommerce.snapkart_orderservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {
    private Long orderId;
    private Double amount;
    private String productName;
}
