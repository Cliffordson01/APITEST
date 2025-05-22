package com.example.demo.controller;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.Model.Reserva;
import com.example.demo.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "Operações relacionadas a reservas de mesas")
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova reserva")
    public ResponseEntity<Reserva> criarReserva(@RequestBody ReservaDTO reservaDTO) {
        Reserva novaReserva = reservaService.criarReserva(reservaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }

    @GetMapping
    @Operation(summary = "Listar todas as reservas")
    public ResponseEntity<List<Reserva>> listarReservas() {
        List<Reserva> reservas = reservaService.listarTodas();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reserva por ID")
    public ResponseEntity<Reserva> buscarPorId(@PathVariable Long id) {
        Reserva reserva = reservaService.buscarPorId(id);
        return ResponseEntity.ok(reserva);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar reservas de um cliente")
    public ResponseEntity<List<Reserva>> listarPorCliente(@PathVariable Long clienteId) {
        List<Reserva> reservas = reservaService.listarPorCliente(clienteId);
        return ResponseEntity.ok(reservas);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da reserva")
    public ResponseEntity<Reserva> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Reserva reserva = reservaService.atualizarStatus(id, status);
        return ResponseEntity.ok(reserva);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar reserva")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }
}