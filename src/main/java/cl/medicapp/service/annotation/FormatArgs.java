package cl.medicapp.service.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación que indica que metodo o clase debe formatear los argumentos de entrada compatible con:
 * - @Capitalize
 * - @UpperCase
 * - @LowerCase
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormatArgs {
}
