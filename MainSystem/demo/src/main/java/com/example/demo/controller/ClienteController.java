package com.example.demo.controller;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.Model.Cliente;
import com.example.demo.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo cliente")
    public ResponseEntity<Cliente> criarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente novoCliente = clienteService.criarCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @GetMapping
    @Operation(summary = "Listar todos os clientes")
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente")
    public ResponseEntity<Cliente> atualizarCliente(
            @PathVariable Long id,
            @RequestBody ClienteDTO clienteDTO) {
        Cliente clienteAtualizado = clienteService.atualizarCliente(id, clienteDTO);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cliente")
    public ResponseEntity<Void> removerCliente(@PathVariable Long id) {
        clienteService.removerCliente(id);
        return ResponseEntity.noContent().build();
    }
}