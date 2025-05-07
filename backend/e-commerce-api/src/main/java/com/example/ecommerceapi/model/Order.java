package com.example.ecommerceapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String userId;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private String status;
    private String paymentMethod;
    private String paymentId;
    private String shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}