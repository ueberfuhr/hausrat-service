package de.sample.hausrat.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The properties from application.yml. You can specify them by the following snippet:
 *
 * <pre>
 * server:
 *   endpoints:
 *     api:
 *       v1: /api/v1
 * </pre>
 */
@Configuration
@ConfigurationProperties(prefix = "server.endpoints.api")
public @Data class ApiEndPointsConfig {

    private String v1 = "/api/v1";

}
