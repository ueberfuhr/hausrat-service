package de.sample.hausrat.config.exceptions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
public class WrappedExceptionAspect {

    @Around("@within(Throw) || @annotation(Throw)")
    //@Around("execution(* de.sample.hausrat.domain.ProductService.*(..))")
    public Object wrapException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            var result = joinPoint.proceed();
            // Flux and Mono both support onErrorMap, but declare it on their own
            if (result instanceof Flux) {
                Flux<?> p = (Flux<?>) result;
                result = p.onErrorMap(e -> this.mapException(joinPoint, e));
            } else if (result instanceof Mono) {
                Mono<?> m = (Mono<?>) result;
                result = m.onErrorMap(e -> this.mapException(joinPoint, e));
            }
            return result;
        } catch (Exception e) {
            throw mapException(joinPoint, e);
        }
    }

    private Throwable mapException(ProceedingJoinPoint joinPoint, Throwable e) {
        var annotation = findAnnotation(joinPoint.getSignature(), WrappedException.class).orElse(null);
        if (null != annotation
          &&
          // include must be empty or exception must be included
          (
            annotation.include().length < 1 || findClassForInstance(e, annotation.include()).isPresent()
          )
          &&
          // exception must not be excluded
          findClassForInstance(e, annotation.exclude()).isEmpty()
        ) {
            try {
                return annotation.wrapper().getDeclaredConstructor().newInstance().apply(e);
            } catch (Exception ex) {
                ex.addSuppressed(e);
                return ex;
            }
        }
        return e;
    }

    private static <T extends Annotation> Optional<T> findAnnotation(AnnotatedElement element, Class<T> annotationType) {
        return Optional.ofNullable(AnnotationUtils.findAnnotation(element, annotationType));
    }

    private static <T extends Annotation> Optional<T> findAnnotation(Signature signature, Class<T> annotationType) {
        if (signature instanceof MethodSignature) {
            var method = ((MethodSignature) signature).getMethod();
            return findAnnotation(method, annotationType)
              .or(() -> findAnnotation(method.getDeclaringClass(), annotationType));
        } else {
            return Optional.empty();
        }
    }

    private static <T> Optional<Class<? extends T>> findClassForInstance(Object o, Class<? extends T>[] classes) {
        return Arrays.stream(classes).filter(c -> c.isInstance(o)).findFirst();
    }

}
