package de.sample.hausrat.domain.exceptions;

import java.util.function.UnaryOperator;

public class ServiceException extends RuntimeException {

    public static class ExceptionWrapper implements UnaryOperator<Throwable> {

        @Override
        public Throwable apply(Throwable cause) {
            return cause instanceof ServiceException ? cause : new ServiceException(cause);
        }
    }

    public ServiceException() {
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
