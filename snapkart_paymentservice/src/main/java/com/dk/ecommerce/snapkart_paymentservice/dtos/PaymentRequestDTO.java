package com.dk.ecommerce.snapkart_paymentservice.dtos;

import com.dk.ecommerce.snapkart_paymentservice.models.Payment;
import com.dk.ecommerce.snapkart_paymentservice.models.PaymentMethod;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PaymentRequestDTO {
    private Long orderId;
    private Double amount;
    private String productName;
}
