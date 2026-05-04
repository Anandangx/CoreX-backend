package com.springbootmain.fullstack_backend.controller;

import com.springbootmain.fullstack_backend.model.User;
import com.springbootmain.fullstack_backend.repository.UserRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://corex-management.vercel.app"
})
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/users")
    public List<User> all() {
        return repo.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return repo.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }

    @PostMapping("/user")
    public ResponseEntity<User> create(@RequestBody User u) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(u));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> update(@RequestBody User u, @PathVariable Long id) {
        return repo.findById(id).map(existing -> {
            existing.setName(u.getName());
            existing.setUsername(u.getUsername());
            existing.setEmail(u.getEmail());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        repo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Deleted"));
    }
}