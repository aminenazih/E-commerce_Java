package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByFeaturedTrue();
    List<Product> findByCategory(String category);
    List<Product> findByNameContainingIgnoreCase(String name);
}