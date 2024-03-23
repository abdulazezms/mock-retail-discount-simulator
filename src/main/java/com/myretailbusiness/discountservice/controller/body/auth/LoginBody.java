package com.myretailbusiness.discountservice.controller.body.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class LoginBody {
    @NotEmpty(message = "username must not be empty")
    @Email(message = "username must be an email")
    private String username;
    @NotEmpty(message = "password must not be empty")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginBody loginBody = (LoginBody) o;
        return Objects.equals(username, loginBody.username) && Objects.equals(password, loginBody.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
