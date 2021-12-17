package de.sample.hausrat.config.exceptions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
public class WrappedExceptionAspect {

    @Around("@within(Throw) || @annotation(Throw)")
    public Object wrapException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw mapException(joinPoint, e);
        }
    }

    private Throwable mapException(ProceedingJoinPoint joinPoint, Throwable e) {
        var annotation = findAnnotation(joinPoint.getSignature()).orElse(null);
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

    private static Optional<WrappedException> findAnnotation(AnnotatedElement element) {
        return Optional.ofNullable(AnnotationUtils.findAnnotation(element, WrappedException.class));
    }

    private static Optional<WrappedException> findAnnotationForMethod(Method method) {
        return findAnnotation(method)
          .or(() -> findAnnotation(method.getDeclaringClass()));
    }

    private static Optional<WrappedException> findAnnotation(Signature signature) {
        return Optional.of(signature)
          .filter(MethodSignature.class::isInstance)
          .map(MethodSignature.class::cast)
          .map(MethodSignature::getMethod)
          .flatMap(WrappedExceptionAspect::findAnnotationForMethod);
    }

    private static <T> Optional<Class<? extends T>> findClassForInstance(Object o, Class<? extends T>[] classes) {
        return Arrays.stream(classes).filter(c -> c.isInstance(o)).findFirst();
    }

}
