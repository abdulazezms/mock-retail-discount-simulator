package com.myretailbusiness.discountservice.config.security;

import com.myretailbusiness.discountservice.constants.APIRoutes;
import com.myretailbusiness.discountservice.constants.AppRolesConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    public static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/users/authenticate"
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(AUTH_WHITELIST)
                                .requestMatchers("/**")
                        .permitAll()

//                        .requestMatchers(APIRoutes.BILLINGS_CONTROLLER_REQUEST_MAPPING + "/**")
//                        .hasAnyRole(AppRolesConstants.CUSTOMER, AppRolesConstants.EMPLOYEE, AppRolesConstants.AFFILIATE)
                );

//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                );
        return http.build();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
        return jwtAuthenticationConverter;
    }
}
