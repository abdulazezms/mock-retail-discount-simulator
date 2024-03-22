package com.myretailbusiness.discountservice.controller.body.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginBody {
    @NotEmpty(message = "username must not be empty")
    @Email(message = "username must be an email")
    private String username;
    @NotEmpty(message = "password must not be empty")
    private String password;
}
