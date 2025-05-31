package com.cardify.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CardifyContextHolder {

    public static String getUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        return (String) context.getAuthentication().getPrincipal();
    }
}
