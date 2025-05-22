// Responsavél: Gabriel Dalecio | Cliffordson Cetoute
package com.example.demo.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;
    
    @Column(nullable = false)
    private LocalDateTime dataReserva;
    
    @Column(nullable = false)
    private LocalTime horaReserva;
    
    @Column(nullable = false)
    private String status; // Confirmada, Cancelada, Concluída
    
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Mesa getMesa() { return mesa; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }
    public LocalDateTime getDataReserva() { return dataReserva; }
    public void setDataReserva(LocalDateTime dataReserva) { this.dataReserva = dataReserva; }
    public LocalTime getHoraReserva() { return horaReserva; }
    public void setHoraReserva(LocalTime horaReserva) { this.horaReserva = horaReserva; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
}