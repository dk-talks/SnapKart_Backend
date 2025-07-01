package com.dk.ecommerce.snapkart_paymentservice.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
//    private Long userId;
    private Long orderId;
    private double amount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // e.g., "CREDIT_CARD", "DEBIT_CARD", "PAYPAL"
    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // e.g., "PENDING", "COMPLETED", "FAILED"
    @Nullable
    private String transactionId; // Unique identifier for the transaction
}
