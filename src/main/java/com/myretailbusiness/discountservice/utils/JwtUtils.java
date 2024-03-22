package com.myretailbusiness.discountservice.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

public final class JwtUtils {
    public static String getUserEmail() {
        JwtAuthenticationToken authenticationPrincipal = (JwtAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        return (String) authenticationPrincipal.getToken().getClaims().get("email");
    }

    public static String getUserRole() {
        // Assuming we only support a single role per user (i.e, AFFILIATE)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleWithPrefix = authorities.iterator().next().getAuthority();
        String role = roleWithPrefix.startsWith("ROLE_") ? roleWithPrefix.substring(5) : roleWithPrefix;
        return role;
    }
}
