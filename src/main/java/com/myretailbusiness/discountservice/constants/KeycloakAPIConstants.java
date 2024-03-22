package com.myretailbusiness.discountservice.constants;

public class KeycloakAPIConstants {
    public final static String REALMS_ENDPOINT = "/realms";
    public final static String TOKEN_ENDPOINT = "/protocol/openid-connect/token";

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

    /**
     * Value of the email claim in Keycloak's issued access tokens.
     */
    public static final String EMAIL_CLAIM = "email";

    /**
     * Value of the createdAt claim in Keycloak's issued access tokens; long unix epoch time.
     */
    public static final String CREATED_AT_CLAIM = "createdAt";
}
