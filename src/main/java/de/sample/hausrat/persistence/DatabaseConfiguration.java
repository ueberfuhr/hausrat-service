package de.sample.hausrat.persistence;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class DatabaseConfiguration {

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer result = new ConnectionFactoryInitializer();
        result.setConnectionFactory(connectionFactory);
        result.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("db-schema.sql")));
        return result;
    }

}
