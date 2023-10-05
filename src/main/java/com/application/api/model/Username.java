package com.application.api.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotBlank(message = "Имя пользователя не может быть пустым")
@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Имя пользователя должно содержать только латинские буквы и цифры")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {
    String message() default "Некорректное имя пользователя";
}
