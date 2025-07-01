package com.dk.ecommerce.snapkart_orderservice.services;

import com.dk.ecommerce.snapkart_orderservice.models.Order;
import com.dk.ecommerce.snapkart_orderservice.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Long userId, String productId, int quantity, double totalPrice) {
        // Logic to create a new order
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setStatus("PENDING"); // Default status
        order.setShippingAddress("Default Address"); // Default shipping address
        order.setTotalPrice(totalPrice); // Default total price, should be calculated based on product price

        return orderRepository.save(order); // Save the order to the database
    }

    public Order getOrderById(Long orderId) {
        // Logic to retrieve an order by its ID
        return orderRepository.findById(orderId)
                .orElse(null); // Return null if order not found
    }

    public Order updateOrder(Long orderId, Order order) {
        // Logic to update an existing order
        return orderRepository.findById(orderId)
                .map(existingOrder -> {
                    existingOrder.setQuantity(order.getQuantity());
                    existingOrder.setStatus(order.getStatus());
                    existingOrder.setUserId(existingOrder.getUserId());
                    existingOrder.setShippingAddress(order.getShippingAddress());
                    existingOrder.setProductId(order.getProductId());
                    existingOrder.setTotalPrice(order.getTotalPrice());
                    return orderRepository.save(existingOrder);
                })
                .orElse(null); // Return null if order not found
    }



    public boolean deleteOrder(Long orderId) {
        // Logic to delete an order
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true; // Deletion successful
        } else {
            return false; // Order not found
        }
    }
}
