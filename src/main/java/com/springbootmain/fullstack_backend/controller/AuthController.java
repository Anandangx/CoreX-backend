package com.springbootmain.fullstack_backend.controller;

import com.springbootmain.fullstack_backend.config.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {

        String username = data.get("username");
        String password = data.get("password");

        // FIX: Return proper ResponseEntity instead of throwing RuntimeException
        // which caused a 500 Internal Server Error instead of 401 Unauthorized.
        if ("admin".equals(username) && "1234".equals(password)) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "username", username,
                    "role", "ADMIN"
            ));
        }

        // FIX: Return 401 with a JSON body — the frontend reads error.response.data.message
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid username or password"));
    }
}