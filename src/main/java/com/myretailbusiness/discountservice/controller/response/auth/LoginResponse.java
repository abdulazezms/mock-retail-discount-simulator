package com.myretailbusiness.discountservice.controller.response.auth;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private long expiresInSeconds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResponse that = (LoginResponse) o;
        return expiresInSeconds == that.expiresInSeconds && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, expiresInSeconds);
    }
}
