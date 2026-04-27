package com.springbootmain.fullstack_backend.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // FIX: Use a fixed base64 secret so tokens survive server restarts.
    // In production, move this to application.properties and use @Value.
    private static final String SECRET = "YourSuperSecretKeyForJWTMustBe256BitsLongAtLeast!!";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final long EXPIRY_MS = 3_600_000; // 1 hour

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_MS))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            getClaims(token); // throws if expired or invalid
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}