package com.dk.ecommerce.snapkart_paymentservice.strategies;

import com.dk.ecommerce.snapkart_paymentservice.adapters.PaymentGatewayAdapter;
import com.dk.ecommerce.snapkart_paymentservice.adapters.RazorPay.RazorPayAdapter;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayStrategy {

    private RazorPayAdapter razorPayAdapter;

    public PaymentGatewayStrategy(RazorPayAdapter razorPayAdapter) {
        this.razorPayAdapter = razorPayAdapter;
    }

    public PaymentGatewayAdapter getPaymentGatewayAdapter() {

        // businesss logic to choose the payment gateway, as of now I am returning Razorpay
//        return
        return razorPayAdapter;
    }

}
