package com.example.demo.validation;

import com.example.demo.Model.Mesa;
import com.example.demo.repository.MesaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class MesaDisponivelValidator implements ConstraintValidator<MesaDisponivel, Long> {

    @Autowired
    private MesaRepository mesaRepository;

    @Override
    public boolean isValid(Long mesaId, ConstraintValidatorContext context) {
        if (mesaId == null) {
            return true;
        }

        Optional<Mesa> mesaOptional = mesaRepository.findById(mesaId);
        return mesaOptional.map(mesa -> "Livre".equals(mesa.getStatus())).orElse(false);
    }
}