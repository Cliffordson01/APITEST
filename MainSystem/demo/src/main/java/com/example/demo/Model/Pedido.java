// Responsavél: Gabriel Dalecio
package com.example.demo.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;
    
    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;
    
    @ManyToOne
    @JoinColumn(name = "item_cardapio_id", nullable = false)
    private ItemCardapio itemCardapio;
    
    @Column(nullable = false)
    private Integer quantidade;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;
    
    @Column(nullable = false)
    private LocalDateTime dataHora;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }
    public Mesa getMesa() { return mesa; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }
    public ItemCardapio getItemCardapio() { return itemCardapio; }
    public void setItemCardapio(ItemCardapio itemCardapio) { this.itemCardapio = itemCardapio; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}