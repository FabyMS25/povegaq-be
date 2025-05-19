package com.gamq.ambiente.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Year;

public class YearFabricacionValidator implements ConstraintValidator<ValidYearFabricacion, Integer> {

    private static final int MIN_YEAR = 1900;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return false; // obligatorio

        int currentYear = Year.now().getValue();

        return value >= MIN_YEAR && value <= currentYear;
    }
}
