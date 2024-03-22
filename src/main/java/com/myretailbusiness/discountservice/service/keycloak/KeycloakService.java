package com.myretailbusiness.discountservice.service.keycloak;

import com.myretailbusiness.discountservice.controller.body.auth.LoginBody;
import com.myretailbusiness.discountservice.controller.response.auth.LoginResponse;
import com.myretailbusiness.discountservice.exception.IncorrectCredentials;
import com.myretailbusiness.discountservice.exception.ServiceUnavailableException;
import com.myretailbusiness.discountservice.httpclient.client.keycloak.KeycloakClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KeycloakService {
    private final KeycloakClient keycloakClient;
    private final String realmName;

    private String clientId;

    private String clientSecret;

    @Autowired
    public KeycloakService(KeycloakClient keycloakClient,
                           @Value("${app.keycloak.client-id}") String clientId,
                           @Value("${app.keycloak.client-secret}") String clientSecret,
                           @Value("${app.keycloak.realm}") String realmName) {
        this.keycloakClient = keycloakClient;
        this.realmName = realmName;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public LoginResponse authenticateUser(LoginBody loginBody) {
        try {
            var response = keycloakClient.authenticateUser(realmName,
                    Map.of(
                            "client_id", clientId,
                            "client_secret", clientSecret,
                            "grant_type", "password",
                            "username", loginBody.getUsername(),
                            "password", loginBody.getPassword())).getBody();
            return LoginResponse.builder()
                    .token(response.getAccessToken())
                    .expiresInSeconds(response.getExpiresIn())
                    .build();
        } catch (FeignException.Unauthorized ex) {
            throw new IncorrectCredentials("Username/password is incorrect");
        } catch (Exception ex) {
            throw new ServiceUnavailableException("Auth service is unavailable now");
        }
    }
}
