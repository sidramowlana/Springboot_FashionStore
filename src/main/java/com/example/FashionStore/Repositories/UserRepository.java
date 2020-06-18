package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String name);

    User findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}