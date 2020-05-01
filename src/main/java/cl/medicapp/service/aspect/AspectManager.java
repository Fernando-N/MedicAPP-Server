package cl.medicapp.service.aspect;

import cl.medicapp.service.annotation.Capitalize;
import cl.medicapp.service.annotation.LowerCase;
import cl.medicapp.service.annotation.UpperCase;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class AspectManager {

    @Around("@within(cl.medicapp.service.annotation.FormatArgs) || @annotation(cl.medicapp.service.annotation.FormatArgs)")
    public Object formatArgs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        for (AtomicInteger i = new AtomicInteger(0); i.intValue() < args.length; i.incrementAndGet()) {

            if (args[i.intValue()] instanceof String) {

                Annotation[] annotations = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getParameterAnnotations()[i.intValue()];
                args[i.intValue()] = formatString(annotations, (String) args[i.intValue()]);

            } else {

                List<FieldWithAnnotation> fields = getFieldsWithAnnotation(args[i.intValue()]);

                fields.forEach(field -> {
                    Method getMethod;
                    Method setMethod;
                    try {
                        getMethod = args[i.intValue()].getClass().getMethod(Constants.GET.concat(StringUtils.capitalize(field.getFieldName())));
                        setMethod = args[i.intValue()].getClass().getDeclaredMethod(Constants.SET.concat(StringUtils.capitalize(field.getFieldName())), String.class);
                    } catch (NoSuchMethodException e) {
                        throw GenericResponseUtil.getGenericException();
                    }

                    String actualValue = Optional.ofNullable((String) ReflectionUtils.invokeMethod(getMethod, args[i.intValue()]))
                            .orElseThrow(GenericResponseUtil::getGenericException);

                    String newValue;

                    switch (field.getAnnotation()) {
                        case Constants.CAPITALIZE:
                            newValue = StringUtil.capitalizeAllWords(actualValue);
                            break;
                        case Constants.UPPERCASE:
                            newValue = actualValue.trim().toUpperCase();
                            break;
                        case Constants.LOWERCASE:
                            newValue = actualValue.trim().toLowerCase();
                            break;
                        default:
                            newValue = actualValue;
                            break;
                    }

                    ReflectionUtils.invokeMethod(setMethod, args[i.intValue()], newValue);
                });


            }
        }

        return proceedingJoinPoint.proceed(args);
    }

    private String formatString(Annotation[] annotations, String arg) {
        if (containsAnnotation(annotations, Capitalize.class)) {
            return StringUtil.capitalizeAllWords(arg);
        } else if (containsAnnotation(annotations, UpperCase.class)) {
            return arg.toUpperCase();
        } else if (containsAnnotation(annotations, LowerCase.class)) {
            return arg.toLowerCase();
        } else {
            return arg;
        }
    }

    private List<FieldWithAnnotation> getFieldsWithAnnotation(Object arg) {
        return Arrays.stream(arg.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Capitalize.class) || field.isAnnotationPresent(UpperCase.class) || field.isAnnotationPresent(LowerCase.class))
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private boolean containsAnnotation(Annotation[] annotationsCheck, Class<?> annotationToValidate) {
        return Arrays.stream(annotationsCheck).anyMatch(annotation -> annotationToValidate.isAssignableFrom(annotation.getClass().getInterfaces()[0]));
    }


    private FieldWithAnnotation convert(Field field) {
        return FieldWithAnnotation.builder()
                .fieldName(field.getName())
                .annotation(
                        Arrays.stream(field.getAnnotations())
                                .filter(annotation -> annotation.getClass().getInterfaces()[0].getName().startsWith(Constants.PACKAGE_BASE))
                                .findFirst()
                                .map(annotation -> annotation.getClass().getInterfaces()[0].getSimpleName())
                                .orElseThrow())
                .build();
    }

    @AllArgsConstructor
    @Getter
    @Builder
    private static class FieldWithAnnotation {
        private final String fieldName;
        private final String annotation;
    }

}
