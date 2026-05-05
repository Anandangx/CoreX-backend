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

    @GetMapping
    public List<User> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return repo.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found")));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(u));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User u, @PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        User existing = repo.findById(id).get();

        if (u.getName() != null)
            existing.setName(u.getName());

        if (u.getUsername() != null)
            existing.setUsername(u.getUsername());

        if (u.getEmail() != null)
            existing.setEmail(u.getEmail());

        return ResponseEntity.ok(repo.save(existing));
    }

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