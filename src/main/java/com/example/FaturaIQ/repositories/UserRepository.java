package com.example.FaturaIQ.repositories;

import com.example.FaturaIQ.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String userName);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
