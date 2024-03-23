package com.myretailbusiness.discountservice.controller.user;

import com.myretailbusiness.discountservice.controller.body.auth.LoginBody;
import com.myretailbusiness.discountservice.controller.response.auth.LoginResponse;

public class UserControllerTestUtil {

    public static LoginBody getValidCredentialsBody(){
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername("username@email.com");
        loginBody.setPassword("password");
        return loginBody;
    }

    public static LoginResponse getLoginResponse() {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("token");
        loginResponse.setExpiresInSeconds(120);
        return loginResponse;
    }


    public static LoginBody getCredentialsBodyUsernameNotEmail() {
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername("notemail");
        loginBody.setPassword("password");
        return loginBody;
    }

}
