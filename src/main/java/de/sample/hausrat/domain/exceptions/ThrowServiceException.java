package de.sample.hausrat.domain.exceptions;

import de.sample.hausrat.config.exceptions.WrappedException;

import javax.validation.ValidationException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@WrappedException(
  wrapper = ServiceException.ExceptionWrapper.class,
  exclude = ValidationException.class
)
public @interface ThrowServiceException {
}
