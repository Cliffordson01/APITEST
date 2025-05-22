package com.example.demo.controller;

import com.example.demo.dto.ItemCardapioDTO;
import com.example.demo.Model.ItemCardapio;
import com.example.demo.service.ItemCardapioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens")
@Tag(name = "Itens do Cardápio", description = "Operações relacionadas aos itens do cardápio")
public class ItemCardapioController {
    private final ItemCardapioService itemCardapioService;

    public ItemCardapioController(ItemCardapioService itemCardapioService) {
        this.itemCardapioService = itemCardapioService;
    }

    @PostMapping
    @Operation(summary = "Adicionar novo item ao cardápio")
    public ResponseEntity<ItemCardapio> adicionarItem(@RequestBody ItemCardapioDTO itemCardapioDTO) {
        ItemCardapio novoItem = itemCardapioService.criarItem(itemCardapioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }

    @GetMapping
    @Operation(summary = "Listar todos os itens do cardápio")
    public ResponseEntity<List<ItemCardapio>> listarItens() {
        List<ItemCardapio> itens = itemCardapioService.listarTodos();
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter item por ID")
    public ResponseEntity<ItemCardapio> obterPorId(@PathVariable Long id) {
        ItemCardapio item = itemCardapioService.buscarPorId(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item do cardápio")
    public ResponseEntity<ItemCardapio> atualizarItem(
            @PathVariable Long id,
            @RequestBody ItemCardapioDTO itemCardapioDTO) {
        ItemCardapio itemAtualizado = itemCardapioService.atualizarItem(id, itemCardapioDTO);
        return ResponseEntity.ok(itemAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover item do cardápio")
    public ResponseEntity<Void> removerItem(@PathVariable Long id) {
        itemCardapioService.removerItem(id);
        return ResponseEntity.noContent().build();
    }
}