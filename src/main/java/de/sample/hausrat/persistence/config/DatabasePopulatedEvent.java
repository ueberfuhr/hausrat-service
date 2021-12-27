package de.sample.hausrat.persistence.config;

import org.springframework.context.ApplicationEvent;

public class DatabasePopulatedEvent extends ApplicationEvent {

    public DatabasePopulatedEvent(Object source) {
        super(source);
    }

}
