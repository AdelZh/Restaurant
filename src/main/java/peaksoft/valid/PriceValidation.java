package peaksoft.valid;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceValidation implements ConstraintValidator<PriceValidator, Integer> {

    @Override
    public boolean isValid(Integer price, ConstraintValidatorContext constraintValidatorContext) {
        return price != null && price > 0;
    }
}
