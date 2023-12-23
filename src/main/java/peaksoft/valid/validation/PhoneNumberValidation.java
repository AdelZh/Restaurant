package peaksoft.valid.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.valid.PhoneNumberValidator;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface PhoneNumberValidation {

    String message() default "phoneNumber must contain +996 and 13 symbols";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}

