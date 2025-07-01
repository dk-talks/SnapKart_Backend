package com.dk.ecommerce.snapkart_paymentservice.adapters.configs;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayConfig {

    @Value("${razorpay.key}")
    private String razorPayKey;
    @Value("${razorpay.secret}")
    private String razorPaySecretKey;

    @Bean
    public RazorpayClient razorpayClient() {
        try {
            return new RazorpayClient(razorPayKey, razorPaySecretKey);
        } catch (RazorpayException e) {
            throw new RuntimeException("Failed to create RazorpayClient", e);
        }
    }
}