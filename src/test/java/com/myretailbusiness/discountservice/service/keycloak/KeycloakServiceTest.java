package com.myretailbusiness.discountservice.service.keycloak;

import com.myretailbusiness.discountservice.controller.body.auth.LoginBody;
import com.myretailbusiness.discountservice.controller.response.auth.LoginResponse;
import com.myretailbusiness.discountservice.domain.Role;
import com.myretailbusiness.discountservice.exception.IncorrectCredentialsException;
import com.myretailbusiness.discountservice.exception.NoResourceFoundException;
import com.myretailbusiness.discountservice.exception.ServiceUnavailableException;
import com.myretailbusiness.discountservice.httpclient.client.keycloak.KeycloakClient;
import com.myretailbusiness.discountservice.httpclient.response.keycloak.KeycloakAuthenticationResponse;
import com.myretailbusiness.discountservice.service.role.RoleServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;
import java.util.Optional;

import static com.myretailbusiness.discountservice.service.keycloak.KeycloakServiceTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KeycloakServiceTest {
    @Mock private KeycloakClient keycloakClient;

    @InjectMocks private KeycloakServiceImpl keycloakService;


    @BeforeEach void setUp() {
        keycloakService = new KeycloakServiceImpl(keycloakClient, CLIENT_ID, CLIENT_SECRET, REALM_NAME);
    }

    @Test public void Should_ReturnLoginResponse_When_CredentialsCorrect() {
        LoginBody correctCredentials = getLoginBody();
        Map<String, ?> body = getResourceOwnerPasswordCredentialsBodyFromUserCredentials(correctCredentials);
        KeycloakAuthenticationResponse keycloakAuthenticationResponse = getKeycloakAuthenticationResponse();
        ResponseEntity<KeycloakAuthenticationResponse> keycloakClientResponse = ResponseEntity
                .of(Optional.of(keycloakAuthenticationResponse));

        when(keycloakClient.authenticateUser(REALM_NAME, body))
                .thenReturn(keycloakClientResponse);
        LoginResponse actualResponse = keycloakService.authenticateUser(correctCredentials);

        assertEquals(actualResponse.getExpiresInSeconds(), keycloakAuthenticationResponse.getExpiresIn());
        assertEquals(actualResponse.getToken(), keycloakAuthenticationResponse.getAccessToken());
        verify(keycloakClient, times(1)).authenticateUser(REALM_NAME, body);
    }

    @Test public void Should_ThrowIncorrectCredentialsException_When_CredentialsIncorrect() {
        LoginBody incorrectCredentials = getLoginBody();
        Map<String, ?> body = getResourceOwnerPasswordCredentialsBodyFromUserCredentials(incorrectCredentials);

        when(keycloakClient.authenticateUser(REALM_NAME, body))
                .thenThrow(FeignException.Unauthorized.class);

        assertThrows(IncorrectCredentialsException.class, () -> keycloakService.authenticateUser(incorrectCredentials));
        verify(keycloakClient, times(1)).authenticateUser(REALM_NAME, body);
    }

    @Test public void Should_ThrowServiceUnavailableException_When_UnknownExceptionOccurInLogin() {
        LoginBody anyBody = getLoginBody();
        Map<String, ?> body = getResourceOwnerPasswordCredentialsBodyFromUserCredentials(anyBody);

        when(keycloakClient.authenticateUser(REALM_NAME, body))
                .thenThrow(NoResourceFoundException.class);

        assertThrows(ServiceUnavailableException.class, () -> keycloakService.authenticateUser(anyBody));
        verify(keycloakClient, times(1)).authenticateUser(REALM_NAME, body);
    }
}
