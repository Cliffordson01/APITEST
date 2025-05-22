package com.example.demo.validation;

import com.example.demo.dto.ReservaDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class ReservaValidator implements ConstraintValidator<ReservaValidation, ReservaDTO> {
    @Override
    public boolean isValid(ReservaDTO reservaDTO, ConstraintValidatorContext context) {
        if (reservaDTO.getHoraReserva() == null || reservaDTO.getDataReserva() == null) {
            return true;
        }

        LocalTime horaReserva = reservaDTO.getHoraReserva();
        if (horaReserva.isBefore(LocalTime.of(11, 0)) || horaReserva.isAfter(LocalTime.of(23, 0))) {
            context.buildConstraintViolationWithTemplate("O restaurante funciona das 11h às 23h")
                  .addPropertyNode("horaReserva")
                  .addConstraintViolation();
            return false;
        }

        if (reservaDTO.getDataReserva().getDayOfWeek() == DayOfWeek.SUNDAY) {
            context.buildConstraintViolationWithTemplate("O restaurante não abre aos domingos")
                  .addPropertyNode("dataReserva")
                  .addConstraintViolation();
            return false;
        }

        return true;
    }
}