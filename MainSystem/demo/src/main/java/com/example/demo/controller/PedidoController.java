package com.example.demo.controller;

import com.example.demo.dto.PedidoDTO;
import com.example.demo.Model.Pedido;
import com.example.demo.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operações relacionadas a pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @Operation(summary = "Criar novo pedido")
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoDTO pedidoDTO) {
        Pedido novoPedido = pedidoService.criarPedido(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @GetMapping
    @Operation(summary = "Listar todos os pedidos")
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter pedido por ID")
    public ResponseEntity<Pedido> obterPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/reserva/{reservaId}")
    @Operation(summary = "Listar pedidos por reserva")
    public ResponseEntity<List<Pedido>> listarPorReserva(@PathVariable Long reservaId) {
        List<Pedido> pedidos = pedidoService.listarPorReserva(reservaId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/mesa/{mesaId}")
    @Operation(summary = "Listar pedidos por mesa")
    public ResponseEntity<List<Pedido>> listarPorMesa(@PathVariable Long mesaId) {
        List<Pedido> pedidos = pedidoService.listarPorMesa(mesaId);
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover pedido")
    public ResponseEntity<Void> removerPedido(@PathVariable Long id) {
        pedidoService.removerPedido(id);
        return ResponseEntity.noContent().build();
    }
}