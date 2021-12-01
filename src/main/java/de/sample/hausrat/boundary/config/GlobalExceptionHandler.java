package de.sample.hausrat.boundary.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * Übersetzt in der Boundary geworfene Exceptions in HTTP Responses. Damit elimiert man einen Großteil der ResponseEntity-Erzeugungen im Controller.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(code = BAD_REQUEST)
    protected void handleValidationException() {
        // nothing to do here, just for annotation scanning
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(code = FORBIDDEN)
    protected void handleAccessDenied(Authentication auth) {
        // log the authorities
        logger.debug(String.format("Request denied for user %s with roles %s.", auth.getPrincipal(), auth.getAuthorities()));
    }

}
