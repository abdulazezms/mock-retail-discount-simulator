package com.myretailbusiness.discountservice.utils;

import com.myretailbusiness.discountservice.constants.AppRolesConstants;
import com.myretailbusiness.discountservice.constants.KeycloakAPIConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public final class JwtUtils {
    public static String getUserEmailClaim() {

        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        assert authentication != null && authentication.isAuthenticated();
        return (String) authentication.getToken().getClaims().get(KeycloakAPIConstants.EMAIL_CLAIM);
    }

    public static String getUserRoleClaim() {
        // Assuming we only support a single role per user (i.e, AFFILIATE)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null && authentication.isAuthenticated();
        return authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority().substring(5))//ROLE_
                .filter(AppRolesConstants.APP_ROLES::contains)
                .findFirst().get();
    }

    public static long getUserCreatedAtClaim() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        assert authentication != null && authentication.isAuthenticated();
        long unixEpochTime = (long)authentication.getToken().getClaims().get(KeycloakAPIConstants.CREATED_AT_CLAIM);
        return unixEpochTime;
    }
}
