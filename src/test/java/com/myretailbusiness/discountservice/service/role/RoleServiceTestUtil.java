package com.myretailbusiness.discountservice.service.role;

import com.myretailbusiness.discountservice.constants.AppRolesConstants;
import com.myretailbusiness.discountservice.domain.Role;

public class RoleServiceTestUtil {

    public static Role getRole(){
        Role role = new Role(AppRolesConstants.CUSTOMER);
        return role;
    }
}
