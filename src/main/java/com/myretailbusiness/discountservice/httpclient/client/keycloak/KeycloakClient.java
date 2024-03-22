package com.myretailbusiness.discountservice.httpclient.client.keycloak;

import com.myretailbusiness.discountservice.constants.KeycloakAPIConstants;
import com.myretailbusiness.discountservice.httpclient.response.keycloak.KeycloakAuthenticationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(
        value = "keycloakClient",
        url = "${app.keycloak.auth-server-url}"
)
public interface KeycloakClient {
    @PostMapping(value = KeycloakAPIConstants.REALMS_ENDPOINT + "/{realm}" + KeycloakAPIConstants.TOKEN_ENDPOINT,
                consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    ResponseEntity<KeycloakAuthenticationResponse> authenticateUser(@PathVariable String realm, Map<String, ?> formParams);
}
