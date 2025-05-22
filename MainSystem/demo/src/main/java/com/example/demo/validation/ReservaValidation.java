package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ReservaValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReservaValidation {
    String message() default "Dados de reserva inválidos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}