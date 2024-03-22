package com.myretailbusiness.discountservice.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public final class JwtUtils {
    public static String getUserEmail() {
        JwtAuthenticationToken authenticationPrincipal = (JwtAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        return (String) authenticationPrincipal.getToken().getClaims().get("email");
    }
}
