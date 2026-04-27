package com.springbootmain.fullstack_backend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req   = (HttpServletRequest) request;
        HttpServletResponse res  = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // FIX: Skip filter for public endpoints — login doesn't need a token
        if (path.startsWith("/auth/")) {
            chain.doFilter(request, response);
            return;
        }

        String auth = req.getHeader("Authorization");

        // FIX: Actually validate the token and return 401 if invalid/missing
        if (auth == null || !auth.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"error\": \"Missing token\"}");
            return;
        }

        String token = auth.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"error\": \"Invalid or expired token\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}