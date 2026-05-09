package com.springbootmain.fullstack_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        if ("admin".equals(username)
                && "1234".equals(password)) {

            return ResponseEntity.ok(
                    Map.of(
                            "token",
                            "sample-jwt-token"
                    )
            );
        }

        return ResponseEntity.status(401)
                .body("Invalid credentials");
    }
}