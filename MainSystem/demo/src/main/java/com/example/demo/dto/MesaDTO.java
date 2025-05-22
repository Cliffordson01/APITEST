package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO para cadastro de mesa")
public class MesaDTO {
    @NotNull
    @Schema(description = "Número da mesa", example = "5")
    private Integer numero;
    
    @NotNull
    @Min(1)
    @Schema(description = "Capacidade da mesa", example = "4")
    private Integer capacidade;
    
    @NotBlank
    @Schema(description = "Status da mesa (Livre, Ocupada, Reservada)", example = "Livre")
    private String status;

    // Getters e Setters
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}