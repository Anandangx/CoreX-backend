package com.springbootmain.fullstack_backend.repository;

import com.springbootmain.fullstack_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}