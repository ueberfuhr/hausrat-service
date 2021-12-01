package de.sample.hausrat.boundary.config;

public class Authorities {

    public static final String CUSTOMER = KeycloakWebSecurityConfig.AUTHORITY_PREFIX + "CUSTOMER";
    public static final String AGENT = KeycloakWebSecurityConfig.AUTHORITY_PREFIX + "AGENT";

    private Authorities() {
    }

}
