package com.myretailbusiness.discountservice.service.keycloak;

import com.myretailbusiness.discountservice.constants.AppRolesConstants;
import com.myretailbusiness.discountservice.controller.body.auth.LoginBody;
import com.myretailbusiness.discountservice.domain.Role;
import com.myretailbusiness.discountservice.httpclient.response.keycloak.KeycloakAuthenticationResponse;

import java.util.Map;

public class KeycloakServiceTestUtil {

    static final String CLIENT_ID = "test-client-id";
    static final String CLIENT_SECRET = "test-client-secret";

    static final String REALM_NAME = "test-realm";

    public static String getRealmName() {
        return "realm";
    }

    public static LoginBody getLoginBody(){
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername("username@example.com");
        loginBody.setPassword("password@example.com");
        return loginBody;
    }

    public static Map<String, ?> getResourceOwnerPasswordCredentialsBodyFromUserCredentials(LoginBody loginBody) {
        return Map.of(
                "client_id", CLIENT_ID,
                "client_secret", CLIENT_SECRET,
                "grant_type", "password",
                "username", loginBody.getUsername(),
                "password", loginBody.getPassword());
    }

    public static KeycloakAuthenticationResponse getKeycloakAuthenticationResponse() {
        KeycloakAuthenticationResponse keycloakAuthenticationResponse = new KeycloakAuthenticationResponse();
        keycloakAuthenticationResponse.setAccessToken("access_token");
        keycloakAuthenticationResponse.setExpiresIn(120L);
        return keycloakAuthenticationResponse;
    }
}
