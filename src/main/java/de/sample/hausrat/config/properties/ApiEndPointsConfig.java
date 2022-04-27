package de.sample.hausrat.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
@ConfigurationProperties(prefix = "server.endpoints.api")
public @Data class ApiEndPointsConfig {

    private String v1 = "/api/v1";

}
