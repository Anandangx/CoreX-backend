package com.springbootmain.fullstack_backend.controller;

import com.springbootmain.fullstack_backend.model.User;
import com.springbootmain.fullstack_backend.repository.UserRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://corex-management.vercel.app"
})
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    // GET ALL USERS
    @GetMapping
    public List<User> all() {
        return repo.findAll();
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return repo.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found")));
    }

    // CREATE USER
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(u));
    }

 // UPDATE USER (NULL SAFE)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User u, @PathVariable Long id) {
        return repo.findById(id).map(existing -> {

            if (u.getName() != null)
                existing.setName(u.getName());

            if (u.getUsername() != null)
                existing.setUsername(u.getUsername());

            if (u.getEmail() != null)
                existing.setEmail(u.getEmail());

            return (ResponseEntity<?>) ResponseEntity.ok(repo.save(existing));

        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)   // ← fix: no underscores
                .body(Map.of("message", "User not found")));
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }
        repo.deleteById(id);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "User deleted successfully"
        ));
    }
}