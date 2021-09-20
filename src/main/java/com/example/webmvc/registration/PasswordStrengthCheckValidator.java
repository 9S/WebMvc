package com.example.webmvc.registration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordStrengthCheckValidator implements ConstraintValidator<PasswordStrengthCheck, String> {
    @Override
    public void initialize(final PasswordStrengthCheck arg0) {
        ConstraintValidator.super.initialize(arg0);
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        return password.length() >= 6;
    }

}
