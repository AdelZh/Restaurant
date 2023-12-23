package peaksoft.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.valid.validation.PasswordValidation;

/**
 * Abdyrazakova Aizada
 */
public class PasswordValidator implements ConstraintValidator<PasswordValidation,String > {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length()>=4;
    }
}
