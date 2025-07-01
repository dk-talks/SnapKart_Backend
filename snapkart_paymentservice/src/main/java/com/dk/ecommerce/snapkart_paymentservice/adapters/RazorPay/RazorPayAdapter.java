package com.dk.ecommerce.snapkart_paymentservice.adapters.RazorPay;

import com.dk.ecommerce.snapkart_paymentservice.adapters.PaymentGatewayAdapter;
import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class RazorPayAdapter implements PaymentGatewayAdapter {

    private RazorpayClient razorpayClient;

    public RazorPayAdapter(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }


    @Override
    public String createPaymentURL(String orderId, Double amount, String customerName, String productName) {
        try {
            // Convert amount to paise (Razorpay uses smallest currency unit)
            int amountInPaise = (int) (amount * 100);

            // Create payment link options
            JSONObject options = new JSONObject();
            options.put("amount", amountInPaise);
            options.put("currency", "INR");
            options.put("description", productName);
            options.put("reference_id", orderId);

            // Add customer details
            JSONObject customer = new JSONObject();
            customer.put("name", customerName);
            customer.put("email", "dks@gmail.com");
            customer.put("contact", "98765543210");
            options.put("customer", customer);

            // Create payment link
            PaymentLink paymentLink = razorpayClient.paymentLink.create(options);
            return paymentLink.get("short_url");
        } catch (RazorpayException e) {
            e.printStackTrace();
            return null;
        }
    }
}
