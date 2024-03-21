package com.myretailbusiness.discountservice.constants;

public class KeycloakAPIConstants {
    public final static String ADMIN_API = "/admin/realms";
    public final static String REALMS_ENDPOINT = "/realms";
    public final static String CLIENT_TOKEN_ENDPOINT = "/protocol/openid-connect/token";

    /**
     * Name of the claim containing the realm level roles
     */
    public static final String CLAIM_REALM_ACCESS = "realm_access";
    /**
     * Name of the claim containing roles. (Applicable to realm and resource level.)
     */
    public static final String CLAIM_ROLES = "roles";

    /**
     * Prefix used for realm level roles.
     */
    public static final String PREFIX_ROLE = "ROLE_";
}
