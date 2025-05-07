package com.example.ecommerceapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.Set;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<String> roles;
    private String address;
    private String phone;
}