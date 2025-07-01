package com.dk.ecommerce.snapkart_paymentservice.controllers;

import com.dk.ecommerce.snapkart_paymentservice.dtos.PaymentRequestDTO;
import com.dk.ecommerce.snapkart_paymentservice.models.PaymentMethod;
import com.dk.ecommerce.snapkart_paymentservice.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment/")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // create a payment
    @PostMapping("/create/")
    public String createPaymentUrl(@RequestBody PaymentRequestDTO requestDTO) {

        try {
            String paymentUrl = paymentService.createPaymentUrl(requestDTO.getOrderId(), requestDTO.getAmount(), requestDTO.getProductName());
            if(paymentUrl != null) {
                return paymentUrl;
            } else {
                return "No Payment Url could be created";
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }

    }

}
