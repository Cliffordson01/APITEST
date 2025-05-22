package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = MesaDisponivelValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MesaDisponivel {
    String message() default "Mesa não está disponível";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}