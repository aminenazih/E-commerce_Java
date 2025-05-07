package com.example.ecommerceapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private List<String> imageUrls;
    private String category;
    private boolean featured;
    private List<String> tags;
}