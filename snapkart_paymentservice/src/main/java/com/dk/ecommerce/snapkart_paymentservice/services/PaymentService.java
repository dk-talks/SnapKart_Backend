package com.dk.ecommerce.snapkart_paymentservice.services;

import com.dk.ecommerce.snapkart_paymentservice.adapters.PaymentGatewayAdapter;
import com.dk.ecommerce.snapkart_paymentservice.adapters.RazorPay.RazorPayAdapter;
import com.dk.ecommerce.snapkart_paymentservice.models.Payment;
import com.dk.ecommerce.snapkart_paymentservice.models.PaymentMethod;
import com.dk.ecommerce.snapkart_paymentservice.models.PaymentStatus;
import com.dk.ecommerce.snapkart_paymentservice.repositories.PaymentRepository;
import com.dk.ecommerce.snapkart_paymentservice.strategies.PaymentGatewayStrategy;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private PaymentGatewayStrategy paymentGatewayStrategy;

    public PaymentService(PaymentRepository paymentRepository, PaymentGatewayStrategy paymentGatewayStrategy) {
        this.paymentRepository = paymentRepository;
        this.paymentGatewayStrategy = paymentGatewayStrategy;
    }

    public String createPaymentUrl(Long orderId, Double amount, String productName) {

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPaymentMethod(PaymentMethod.UPI);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setOrderId(orderId);

        paymentRepository.save(payment);

        PaymentGatewayAdapter paymentGatewayAdapter = paymentGatewayStrategy.getPaymentGatewayAdapter();
        try {

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        String payment1 = paymentGatewayAdapter.createPaymentURL(
                String.valueOf(orderId),
                amount,
                "Dinesh Sharma",
                productName
        );

        return payment1;
    }

}
