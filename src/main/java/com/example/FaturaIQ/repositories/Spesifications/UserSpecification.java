package com.example.FaturaIQ.repositories.Spesifications;

import com.example.FaturaIQ.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> hasUsername(String username) {
        return (root, query, cb) ->
                username == null ? null : cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, cb) ->
                email == null ? null : cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }
}
