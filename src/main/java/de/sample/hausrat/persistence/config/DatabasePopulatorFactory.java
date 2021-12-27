package de.sample.hausrat.persistence.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.r2dbc.connection.init.DatabasePopulator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabasePopulatorFactory {

    private final ApplicationEventPublisher eventPublisher;

    DatabasePopulator eventPublishing(final DatabasePopulator delegate) {
        return connection -> delegate.populate(connection)
          .flatMap(x -> {
              eventPublisher.publishEvent(new DatabasePopulatedEvent(this));
              return null;
          });
    }

}
