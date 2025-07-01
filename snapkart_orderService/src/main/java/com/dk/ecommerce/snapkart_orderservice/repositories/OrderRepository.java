package com.dk.ecommerce.snapkart_orderservice.repositories;


import com.dk.ecommerce.snapkart_orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
