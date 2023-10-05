package com.application.api.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Пароль должен содержать только цифры и иметь длину от 4 до 6 символов";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
