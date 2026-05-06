package com.springbootmain.fullstack_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {

        String username = data.get("username");
        String password = data.get("password");

        if ("admin".equals(username) && "1234".equals(password)) {
            return ResponseEntity.ok(Map.of(
                    "token", "dummy-token-123"
            ));
        }

        return ResponseEntity.status(401).body(Map.of(
                "message", "Invalid credentials"
        ));
    }
}