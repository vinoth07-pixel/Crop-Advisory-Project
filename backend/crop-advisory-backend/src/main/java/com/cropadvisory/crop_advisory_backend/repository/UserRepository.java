package com.cropadvisory.crop_advisory_backend.repository;

import com.cropadvisory.crop_advisory_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}