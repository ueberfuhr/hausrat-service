package de.sample.hausrat.config.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
  CalculationCurrencyProperties.class,
  ApiEndPointsConfig.class
})
public class ApplicationConfig {
}
