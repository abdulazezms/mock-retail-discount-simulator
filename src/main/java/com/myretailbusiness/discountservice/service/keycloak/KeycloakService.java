package com.myretailbusiness.discountservice.service.keycloak;

import com.myretailbusiness.discountservice.controller.body.auth.LoginBody;
import com.myretailbusiness.discountservice.controller.response.auth.LoginResponse;

public interface KeycloakService {
    LoginResponse authenticateUser(LoginBody loginBody);
}
