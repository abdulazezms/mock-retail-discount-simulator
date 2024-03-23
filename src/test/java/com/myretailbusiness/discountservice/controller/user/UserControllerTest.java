package com.myretailbusiness.discountservice.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretailbusiness.discountservice.constants.APIRoutes;
import com.myretailbusiness.discountservice.constants.AppProfiles;
import com.myretailbusiness.discountservice.controller.UserController;
import com.myretailbusiness.discountservice.exception.IncorrectCredentialsException;
import com.myretailbusiness.discountservice.exception.ServiceUnavailableException;
import com.myretailbusiness.discountservice.service.keycloak.KeycloakService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.myretailbusiness.discountservice.controller.user.UserControllerTestUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class)
@ActiveProfiles(AppProfiles.UNIT_TEST_PROFILE)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private KeycloakService keycloakService;

    @Test public void Should_ReturnOkStatusCode_When_CredentialsValid() throws Exception {
        when(keycloakService.authenticateUser(getValidCredentialsBody())).thenReturn(getLoginResponse());
        mockMvc
                .perform(
                        post(APIRoutes.USERS_CONTROLLER_REQUEST_MAPPING)
                        .content(objectMapper.writeValueAsString(getValidCredentialsBody()))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(getLoginResponse())));
        verify(keycloakService, times(1)).authenticateUser(getValidCredentialsBody());
    }

    @Test public void Should_ReturnBadRequestStatusCode_When_UsernameIsNotEmail() throws Exception {
        mockMvc
                .perform(
                        post(APIRoutes.USERS_CONTROLLER_REQUEST_MAPPING)
                                .content(objectMapper.writeValueAsString(getCredentialsBodyUsernameNotEmail()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
        verify(keycloakService, never()).authenticateUser(any());
    }

    @Test public void Should_ReturnBadRequestStatusCode_When_HttpMessageNotReadable() throws Exception {
        mockMvc
                .perform(
                        post(APIRoutes.USERS_CONTROLLER_REQUEST_MAPPING)
                                .content(objectMapper.writeValueAsString("non-valid"))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
        verify(keycloakService, never()).authenticateUser(any());
    }

    @Test public void Should_ReturnUnauthorizedStatusCode_When_CredentialsInvalid() throws Exception {
        when(keycloakService.authenticateUser(getValidCredentialsBody()))
                .thenThrow(new IncorrectCredentialsException(""));
        mockMvc
                .perform(
                        post(APIRoutes.USERS_CONTROLLER_REQUEST_MAPPING)
                                .content(objectMapper.writeValueAsString(getValidCredentialsBody()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
        verify(keycloakService, times(1)).authenticateUser(getValidCredentialsBody());
    }

    @Test public void Should_ReturnServiceUnavailableStatusCode_When_KeycloakIsUnavailable() throws Exception {
        when(keycloakService.authenticateUser(getValidCredentialsBody()))
                .thenThrow(new ServiceUnavailableException(""));
        mockMvc
                .perform(
                        post(APIRoutes.USERS_CONTROLLER_REQUEST_MAPPING)
                                .content(objectMapper.writeValueAsString(getValidCredentialsBody()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isServiceUnavailable());
        verify(keycloakService, times(1)).authenticateUser(getValidCredentialsBody());
    }
}
