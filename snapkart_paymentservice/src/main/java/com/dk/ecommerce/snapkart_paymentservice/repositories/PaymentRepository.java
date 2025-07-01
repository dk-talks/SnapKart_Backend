package com.dk.ecommerce.snapkart_paymentservice.repositories;

import com.dk.ecommerce.snapkart_paymentservice.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
