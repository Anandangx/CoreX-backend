package com.springbootmain.fullstack_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // FIX: "user" is a reserved word in MySQL — always use "users"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // FIX: explicit IDENTITY strategy
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}