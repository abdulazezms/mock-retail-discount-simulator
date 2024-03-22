package com.myretailbusiness.discountservice.config.security;

import com.myretailbusiness.discountservice.utils.JwtUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class AppAuditorAware implements AuditorAware<String> {

    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null)
            return Optional.of("SYSTEM_ADMIN");
        else if(authentication.isAuthenticated())
            return Optional.of(JwtUtils.getUserEmail());
        else return Optional.of("ANONYMOUS");
    }
}
