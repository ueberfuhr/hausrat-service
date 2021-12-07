package de.sample.hausrat.security.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Optional;

@UtilityClass
public class SecurityConstants {

    public static final String SECURITY_PROFILE = "security";
    public static final String AUTHORITY_PREFIX = "ROLE_"; // Spring Security Default

    @UtilityClass
    public static class Authorities {

        public static final String CUSTOMER = AUTHORITY_PREFIX + "CUSTOMER";
        public static final String AGENT = AUTHORITY_PREFIX + "AGENT";

    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum Roles {

        CUSTOMER(Authorities.CUSTOMER, "customer"),
        AGENT(Authorities.AGENT, "agent");

        private final String authority;
        private final String name;

        public static Optional<Roles> findByAuthority(String authority) {
            return Arrays.stream(Roles.values())
              .filter(r -> authority.equals(r.getAuthority()))
              .findFirst();
        }
    }

}
