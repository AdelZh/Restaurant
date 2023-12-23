package peaksoft.valid.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.valid.EmailValidator;

import java.lang.annotation.*;

/**
 * Abdyrazakova Aizada
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface EmailValidation {

    String message() default "EmailValid mush ended with '@' and '.com";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
