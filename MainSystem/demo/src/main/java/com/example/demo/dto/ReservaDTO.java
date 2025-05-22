package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "DTO para cadastro de reserva")
public class ReservaDTO {
    @NotNull
    @Schema(description = "ID do cliente", example = "1")
    private Long clienteId;
    
    @NotNull
    @Schema(description = "ID da mesa", example = "1")
    private Long mesaId;
    
    @Future
    @Schema(description = "Data da reserva", example = "2023-12-31")
    private LocalDate dataReserva;
    
    @Schema(description = "Hora da reserva", example = "19:30:00")
    private LocalTime horaReserva;

    // Getters e Setters
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getMesaId() { return mesaId; }
    public void setMesaId(Long mesaId) { this.mesaId = mesaId; }
    public LocalDate getDataReserva() { return dataReserva; }
    public void setDataReserva(LocalDate dataReserva) { this.dataReserva = dataReserva; }
    public LocalTime getHoraReserva() { return horaReserva; }
    public void setHoraReserva(LocalTime horaReserva) { this.horaReserva = horaReserva; }
}