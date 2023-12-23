package peaksoft.valid.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.valid.PasswordValidator;

import java.lang.annotation.*;

/**
 * Abdyrazakova Aizada
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface PasswordValidation {

    String message() default "Password must by length > 4";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}