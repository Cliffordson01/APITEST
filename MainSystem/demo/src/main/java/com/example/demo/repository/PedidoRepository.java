// Responsavél: Gabriel Dalecio
package com.example.demo.repository;

import com.example.demo.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByReservaId(Long reservaId);
    List<Pedido> findByMesaId(Long mesaId);
}