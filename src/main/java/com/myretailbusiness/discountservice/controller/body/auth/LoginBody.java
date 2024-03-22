package com.myretailbusiness.discountservice.controller.body.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginBody {
    private String username;
    private String password;
}
