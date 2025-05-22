// Responsavél: Gabriel Ribeiro | Cliffordson Cetoute
package com.example.demo.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private Integer numero;
    
    @Column(nullable = false)
    private Integer capacidade;
    
    @Column(nullable = false)
    private String status; // Livre, Ocupada, Reservada
    
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;
    
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Reserva> getReservas() { return reservas; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }
    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
}