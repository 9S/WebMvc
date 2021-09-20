package com.example.webmvc.registration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDto> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDto value, ConstraintValidatorContext context) {
        System.out.println(value);
        return value.getPassword().equals(value.getPassword_2());
    }
}
