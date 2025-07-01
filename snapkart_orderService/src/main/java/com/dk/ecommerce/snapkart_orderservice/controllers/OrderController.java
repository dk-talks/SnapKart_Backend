package com.dk.ecommerce.snapkart_orderservice.controllers;

import com.dk.ecommerce.snapkart_orderservice.dtos.OrderRequestDTO;
import com.dk.ecommerce.snapkart_orderservice.dtos.PaymentRequestDTO;
import com.dk.ecommerce.snapkart_orderservice.dtos.ProductResponseDTO;
import com.dk.ecommerce.snapkart_orderservice.models.Order;
import com.dk.ecommerce.snapkart_orderservice.services.OrderService;
import netscape.javascript.JSObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("order/")
public class OrderController {

    private final RestTemplate restTemplate;
    private OrderService orderService;

    public OrderController(OrderService orderService, RestTemplate restTemplate) {
        this.orderService = orderService;
        this.restTemplate = restTemplate;
    }

     @GetMapping("/{orderId}")
     public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {

            Order order = orderService.getOrderById(orderId);
            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.notFound().build();
            }
     }

     @PostMapping("/")
     public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {

            // check if userId, productId, and quantity are valid
            String userUrl = "http://SnapKartUserService/users/" + orderRequestDTO.getUserId();
            String productUrl = "http://SnapKartProductService/products/internal/" + orderRequestDTO.getProductId();

            Object userObject = restTemplate.getForObject(userUrl, Object.class);

            if(userObject == null) {
                // user not present
                System.out.println("User not found: " + orderRequestDTO.getUserId());
                return ResponseEntity.status(400).body(null); // Bad Request if user does not exist
            }

            ProductResponseDTO productResponseDTO = restTemplate.getForObject(productUrl, ProductResponseDTO.class);

            if(productResponseDTO != null) {
                // create order
                Order createdOrder = orderService.createOrder(
                        orderRequestDTO.getUserId(),
                        orderRequestDTO.getProductId(),
                        orderRequestDTO.getQuantity(),
                        productResponseDTO.getPrice() * orderRequestDTO.getQuantity()
                );

                if(createdOrder == null) {
                    return ResponseEntity.status(400).build(); // Bad Request if order creation fails
                }

                // Initiate the payment now
                String paymentUrl = "http://SnapKartPaymentService/payment/create/";
                PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
                paymentRequestDTO.setAmount(createdOrder.getTotalPrice());
                paymentRequestDTO.setOrderId(createdOrder.getId());
                paymentRequestDTO.setProductName(productResponseDTO.getName());

                ResponseEntity<String> paymentResponseEntity = restTemplate.postForEntity(paymentUrl, paymentRequestDTO, String.class);

                if(paymentResponseEntity.getBody() == null) {
                    // payment creation failed
                    System.out.println("Payment creation failed for order: " + createdOrder.getId());
                    return ResponseEntity.status(500).body(null); // Internal Server Error if payment creation fails
                }

                // Payment URL created successfully
                System.out.println("Payment URL: " + paymentResponseEntity.getBody());

                return ResponseEntity.ok(createdOrder); // Return the created order

            } else {
                // product not present
                System.out.println("Product not found: " + orderRequestDTO.getProductId());
                return ResponseEntity.status(400).body(null); // Bad Request if product does not exist
            }

     }

     @PutMapping("/{orderId}")
     public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order order) {
            // Logic to update an existing order
            Order updatedOrder = orderService.updateOrder(orderId, order);
            if (updatedOrder != null) {
                return ResponseEntity.ok(updatedOrder);
            } else {
                return ResponseEntity.notFound().build(); // Not Found if order does not exist
            }
     }

     @DeleteMapping("/{orderId}")
     public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
            // Logic to delete an order
            boolean isDeleted = orderService.deleteOrder(orderId);
            if (isDeleted) {
                return ResponseEntity.noContent().build(); // No Content if deletion is successful
            } else {
                return ResponseEntity.notFound().build(); // Not Found if order does not exist
            }
     }

}
