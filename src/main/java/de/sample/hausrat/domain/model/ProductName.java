package de.sample.hausrat.domain.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {})
@NotNull
@Pattern(regexp = "[A-Z]+[A-Z0-9]*")
@ReportAsSingleViolation
@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductName {

    String message() default "must start with an uppercase letter and only contain uppercase letters and digits";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
