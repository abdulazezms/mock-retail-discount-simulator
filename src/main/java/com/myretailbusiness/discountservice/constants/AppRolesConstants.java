package com.myretailbusiness.discountservice.constants;

import java.util.Set;

public class AppRolesConstants {
    public static final String EMPLOYEE = "EMPLOYEE";
    public static final String CUSTOMER = "CUSTOMER";
    public static final String AFFILIATE = "AFFILIATE";
    public static final Set<String> APP_ROLES = Set.of(EMPLOYEE, CUSTOMER, AFFILIATE);
}