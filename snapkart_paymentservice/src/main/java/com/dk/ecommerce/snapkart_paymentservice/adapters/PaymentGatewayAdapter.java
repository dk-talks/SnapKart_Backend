package com.dk.ecommerce.snapkart_paymentservice.adapters;

import com.razorpay.Payment;

public interface PaymentGatewayAdapter {

    public String createPaymentURL(String orderId, Double amount, String customerName, String productName);

}
