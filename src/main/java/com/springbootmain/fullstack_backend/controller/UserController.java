package com.springbootmain.fullstack_backend.controller;

import com.springbootmain.fullstack_backend.model.User;
import com.springbootmain.fullstack_backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    // GET all users
    @GetMapping("/users")
    public List<User> all() {
        return repo.findAll();
    }

    // GET user by id
    @GetMapping("/user/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return repo.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with id: " + id));
    }

    // CREATE user
    @PostMapping("/user")
    public ResponseEntity<User> create(@RequestBody User u) {
        User saved = repo.save(u);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // UPDATE user — FIX: was using orElseThrow() with no message (bad 500 error)
    @PutMapping("/user/{id}")
    public ResponseEntity<?> update(@RequestBody User u, @PathVariable Long id) {
        return repo.findById(id).map(existing -> {
            existing.setName(u.getName());
            existing.setUsername(u.getUsername());
            existing.setEmail(u.getEmail());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null));
    }

    // DELETE user
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + id);
        }
        repo.deleteById(id);
        return ResponseEntity.ok(
                java.util.Map.of("message", "User deleted successfully")
        );
    }
}