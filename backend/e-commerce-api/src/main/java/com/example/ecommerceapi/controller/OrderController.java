package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.model.Order;
import com.example.ecommerceapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable String userId) {
        return orderRepository.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String id, @RequestBody String status) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(status);
                    order.setUpdatedAt(LocalDateTime.now());
                    return ResponseEntity.ok(orderRepository.save(order));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}