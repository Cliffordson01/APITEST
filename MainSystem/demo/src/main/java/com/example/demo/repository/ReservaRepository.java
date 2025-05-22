// Responsavél: Gabriel Dalecio | Cliffordson Cetoute
package com.example.demo.repository;

import com.example.demo.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByClienteId(Long clienteId);
    
    @Query("SELECT r FROM Reserva r WHERE r.mesa.id = :mesaId AND r.dataReserva = :data AND r.horaReserva = :hora AND r.status = 'Confirmada'")
    Optional<Reserva> findReservaConflitante(Long mesaId, LocalDate data, LocalTime hora);
    
    List<Reserva> findByDataReservaAndStatus(LocalDate data, String status);
}