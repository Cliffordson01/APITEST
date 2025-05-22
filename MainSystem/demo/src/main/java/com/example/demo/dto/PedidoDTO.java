package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "DTO para cadastro de pedido")
public class PedidoDTO {
    @NotNull
    @Schema(description = "ID da reserva", example = "1")
    private Long reservaId;
    
    @NotNull
    @Schema(description = "ID da mesa", example = "1")
    private Long mesaId;
    
    @NotNull
    @Schema(description = "ID do item do cardápio", example = "1")
    private Long itemCardapioId;
    
    @NotNull
    @Min(1)
    @Schema(description = "Quantidade do item", example = "2")
    private Integer quantidade;
    
    @NotNull
    @Positive
    @Schema(description = "Valor total do pedido", example = "91.80")
    private BigDecimal valorTotal;

    // Getters e Setters
    public Long getReservaId() { return reservaId; }
    public void setReservaId(Long reservaId) { this.reservaId = reservaId; }
    public Long getMesaId() { return mesaId; }
    public void setMesaId(Long mesaId) { this.mesaId = mesaId; }
    public Long getItemCardapioId() { return itemCardapioId; }
    public void setItemCardapioId(Long itemCardapioId) { this.itemCardapioId = itemCardapioId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
}