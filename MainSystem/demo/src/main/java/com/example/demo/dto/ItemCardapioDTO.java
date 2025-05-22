package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "DTO para cadastro de item do cardápio")
public class ItemCardapioDTO {
    @NotBlank
    @Size(max = 100)
    @Schema(description = "Nome do item", example = "Pizza Margherita")
    private String nome;
    
    @Size(max = 255)
    @Schema(description = "Descrição do item", example = "Pizza com mussarela e manjericão")
    private String descricao;
    
    @NotNull
    @Positive
    @Schema(description = "Preço do item", example = "45.90")
    private BigDecimal preco;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
}