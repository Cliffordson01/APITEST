package com.example.demo.service;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.Exception.BusinessException;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.Cliente;
import com.example.demo.Model.Mesa;
import com.example.demo.Model.Reserva;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MesaRepository;
import com.example.demo.repository.ReservaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final MesaRepository mesaRepository;

    public ReservaService(ReservaRepository reservaRepository, 
                         ClienteRepository clienteRepository,
                         MesaRepository mesaRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.mesaRepository = mesaRepository;
    }

    @Transactional
    public Reserva criarReserva(ReservaDTO reservaDTO) {
        Cliente cliente = clienteRepository.findById(reservaDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        
        Mesa mesa = mesaRepository.findById(reservaDTO.getMesaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mesa não encontrada"));
        
        if (!mesa.getStatus().equals("Livre")) {
            throw new BusinessException("Mesa não está disponível para reserva");
        }
        
        LocalDateTime dataHoraReserva = reservaDTO.getDataReserva().atTime(reservaDTO.getHoraReserva());
        if (dataHoraReserva.isBefore(LocalDateTime.now())) {
            throw new BusinessException("Não é possível fazer reservas para datas/horários passados");
        }
        
        if (reservaRepository.findReservaConflitante(
                reservaDTO.getMesaId(), 
                reservaDTO.getDataReserva(), 
                reservaDTO.getHoraReserva()).isPresent()) {
            throw new BusinessException("Já existe uma reserva para esta mesa no horário selecionado");
        }
        
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setMesa(mesa);
        reserva.setDataReserva(reservaDTO.getDataReserva().atStartOfDay());
        reserva.setHoraReserva(reservaDTO.getHoraReserva());
        reserva.setStatus("Confirmada");
        
        mesa.setStatus("Reservada");
        mesaRepository.save(mesa);
        
        return reservaRepository.save(reserva);
    }

    @Transactional(readOnly = true)
    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Reserva> listarPorCliente(Long clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }

    @Transactional
    public Reserva atualizarStatus(Long id, String status) {
        if (!List.of("Confirmada", "Cancelada", "Concluída").contains(status)) {
            throw new BusinessException("Status inválido");
        }
        
        Reserva reserva = buscarPorId(id);
        reserva.setStatus(status);
        
        if (status.equals("Cancelada") || status.equals("Concluída")) {
            Mesa mesa = reserva.getMesa();
            mesa.setStatus("Livre");
            mesaRepository.save(mesa);
        }
        
        return reservaRepository.save(reserva);
    }

    @Transactional
    public void cancelarReserva(Long id) {
        Reserva reserva = buscarPorId(id);
        reserva.setStatus("Cancelada");
        
        Mesa mesa = reserva.getMesa();
        mesa.setStatus("Livre");
        mesaRepository.save(mesa);
        
        reservaRepository.save(reserva);
    }

    @Scheduled(fixedRate = 1800000) // Executa a cada 30 minutos
    @Transactional
    public void cancelarReservasNaoConfirmadas() {
        LocalDateTime limite = LocalDateTime.now().minusMinutes(30);
        List<Reserva> reservasParaCancelar = reservaRepository
                .findByDataReservaAndStatus(LocalDate.now(), "Pendente");
        
        reservasParaCancelar.forEach(reserva -> {
            if (reserva.getDataReserva().isBefore(limite)) {
                reserva.setStatus("Cancelada");
                Mesa mesa = reserva.getMesa();
                mesa.setStatus("Livre");
                mesaRepository.save(mesa);
                reservaRepository.save(reserva);
            }
        });
    }
}