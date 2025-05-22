package com.example.demo.controller;

import com.example.demo.dto.MesaDTO;
import com.example.demo.Model.Mesa;
import com.example.demo.service.MesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@Tag(name = "Mesas", description = "Operações relacionadas a mesas do restaurante")
public class MesaController {
    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova mesa")
    public ResponseEntity<Mesa> criarMesa(@RequestBody MesaDTO mesaDTO) {
        Mesa novaMesa = mesaService.criarMesa(mesaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMesa);
    }

    @GetMapping
    @Operation(summary = "Listar todas as mesas")
    public ResponseEntity<List<Mesa>> listarMesas() {
        List<Mesa> mesas = mesaService.listarTodas();
        return ResponseEntity.ok(mesas);
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar mesas disponíveis")
    public ResponseEntity<List<Mesa>> listarMesasDisponiveis() {
        List<Mesa> mesas = mesaService.listarDisponiveis();
        return ResponseEntity.ok(mesas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar mesa por ID")
    public ResponseEntity<Mesa> buscarPorId(@PathVariable Long id) {
        Mesa mesa = mesaService.buscarPorId(id);
        return ResponseEntity.ok(mesa);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar mesa")
    public ResponseEntity<Mesa> atualizarMesa(
            @PathVariable Long id,
            @RequestBody MesaDTO mesaDTO) {
        Mesa mesaAtualizada = mesaService.atualizarMesa(id, mesaDTO);
        return ResponseEntity.ok(mesaAtualizada);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da mesa")
    public ResponseEntity<Mesa> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Mesa mesa = mesaService.atualizarStatus(id, status);
        return ResponseEntity.ok(mesa);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover mesa")
    public ResponseEntity<Void> removerMesa(@PathVariable Long id) {
        mesaService.removerMesa(id);
        return ResponseEntity.noContent().build();
    }
}