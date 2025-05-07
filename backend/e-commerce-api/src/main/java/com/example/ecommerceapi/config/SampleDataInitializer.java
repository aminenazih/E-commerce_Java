package com.example.ecommerceapi.config;

import com.example.ecommerceapi.model.Product;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.repository.ProductRepository;
import com.example.ecommerceapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
public class SampleDataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if we already have products
        if (productRepository.count() == 0) {
            // Add sample products
            Product product1 = new Product();
            product1.setName("Smartphone X");
            product1.setDescription("Latest smartphone with amazing features");
            product1.setPrice(new BigDecimal("799.99"));
            product1.setStockQuantity(100);
            product1.setImageUrls(Collections.singletonList("https://via.placeholder.com/300"));
            product1.setCategory("Electronics");
            product1.setFeatured(true);
            product1.setTags(Arrays.asList("smartphone", "electronics", "gadget"));

            Product product2 = new Product();
            product2.setName("Laptop Pro");
            product2.setDescription("High-performance laptop for professionals");
            product2.setPrice(new BigDecimal("1299.99"));
            product2.setStockQuantity(50);
            product2.setImageUrls(Collections.singletonList("https://via.placeholder.com/300"));
            product2.setCategory("Electronics");
            product2.setFeatured(true);
            product2.setTags(Arrays.asList("laptop", "electronics", "computer"));

            Product product3 = new Product();
            product3.setName("Wireless Headphones");
            product3.setDescription("Premium wireless headphones with noise cancellation");
            product3.setPrice(new BigDecimal("199.99"));
            product3.setStockQuantity(200);
            product3.setImageUrls(Collections.singletonList("https://via.placeholder.com/300"));
            product3.setCategory("Electronics");
            product3.setFeatured(false);
            product3.setTags(Arrays.asList("headphones", "audio", "wireless"));

            productRepository.saveAll(Arrays.asList(product1, product2, product3));
        }

        // Check if we already have users
        if (userRepository.count() == 0) {
            // Create admin user
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setRoles(new HashSet<>(Arrays.asList("ROLE_ADMIN", "ROLE_USER")));

            // Create regular user
            User regularUser = new User();
            regularUser.setUsername("user");
            regularUser.setEmail("user@example.com");
            regularUser.setPassword(passwordEncoder.encode("user123"));
            regularUser.setFirstName("Regular");
            regularUser.setLastName("User");
            regularUser.setRoles(Collections.singleton("ROLE_USER"));

            userRepository.saveAll(Arrays.asList(adminUser, regularUser));
        }
    }
}