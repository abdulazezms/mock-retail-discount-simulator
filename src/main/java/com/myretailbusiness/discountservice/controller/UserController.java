package com.myretailbusiness.discountservice.controller;

import com.myretailbusiness.discountservice.constants.APIRoutes;
import com.myretailbusiness.discountservice.controller.body.auth.LoginBody;
import com.myretailbusiness.discountservice.controller.response.auth.LoginResponse;
import com.myretailbusiness.discountservice.service.bill.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIRoutes.USERS_CONTROLLER_REQUEST_MAPPING)
public class UserController {
    private final KeycloakService keycloakService;

    @Autowired
    public UserController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginBody loginBody) {
        return new ResponseEntity<>(keycloakService.authenticateUser(loginBody), HttpStatus.OK);
    }
}