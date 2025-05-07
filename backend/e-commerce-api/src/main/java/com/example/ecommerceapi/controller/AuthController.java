package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton("ROLE_USER"));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            User user = userOptional.get();
            Map<String, Object> response = new HashMap<>();
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            response.put("roles", user.getRoles());
            response.put("token", "dummy-jwt-token"); // In a real app, generate a real JWT
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }
}