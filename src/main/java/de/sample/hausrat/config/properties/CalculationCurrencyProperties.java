package de.sample.hausrat.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The properties from application.yml. You can specify them by the following snippet:
 *
 * <pre>
 * calculation:
 *   currency:
 *     precision: 2
 *     code: EUR
 * </pre>
 */
@ConfigurationProperties(prefix = "calculation.currency")
public @Data class CalculationCurrencyProperties {

    private int precision = 2;
    private String code = "EUR";

}
