package de.sample.hausrat.config.exceptions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.UnaryOperator;

@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface WrappedException {

    Class<? extends Throwable>[] include() default {};

    Class<? extends Throwable>[] exclude() default {};

    Class<? extends UnaryOperator<Throwable>> wrapper();

}
