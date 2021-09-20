package com.example.webmvc.registration;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordStrengthCheckValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface PasswordStrengthCheck {

    String message() default "zu kurz";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}