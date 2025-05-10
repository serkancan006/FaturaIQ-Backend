package com.example.FaturaIQ.utils;

import com.example.FaturaIQ.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    public User getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Kullanıcı doğrulanmamış.");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        } else {
            throw new IllegalArgumentException("Geçersiz kullanıcı bilgisi.");
        }
    }

    public Integer getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
