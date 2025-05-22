package com.example.demo.service;

import com.example.demo.dto.MesaDTO;
import com.example.demo.Exception.BusinessException;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.Mesa;
import com.example.demo.repository.MesaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MesaService {
    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @Transactional
    public Mesa criarMesa(MesaDTO mesaDTO) {
        if (mesaRepository.existsByNumero(mesaDTO.getNumero())) {
            throw new BusinessException("Já existe uma mesa com este número");
        }
        
        Mesa mesa = new Mesa();
        mesa.setNumero(mesaDTO.getNumero());
        mesa.setCapacidade(mesaDTO.getCapacidade());
        mesa.setStatus(mesaDTO.getStatus());
        
        return mesaRepository.save(mesa);
    }

    @Transactional(readOnly = true)
    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mesa buscarPorId(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa não encontrada com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Mesa> listarDisponiveis() {
        return mesaRepository.findByStatus("Livre");
    }

    @Transactional
    public Mesa atualizarMesa(Long id, MesaDTO mesaDTO) {
        Mesa mesa = buscarPorId(id);
        
        if (!mesa.getNumero().equals(mesaDTO.getNumero()) && 
            mesaRepository.existsByNumero(mesaDTO.getNumero())) {
            throw new BusinessException("Já existe uma mesa com este número");
        }
        
        mesa.setNumero(mesaDTO.getNumero());
        mesa.setCapacidade(mesaDTO.getCapacidade());
        
        return mesaRepository.save(mesa);
    }

    @Transactional
    public Mesa atualizarStatus(Long id, String status) {
        if (!List.of("Livre", "Ocupada", "Reservada").contains(status)) {
            throw new BusinessException("Status inválido");
        }
        
        Mesa mesa = buscarPorId(id);
        mesa.setStatus(status);
        
        return mesaRepository.save(mesa);
    }

    @Transactional
    public void removerMesa(Long id) {
        Mesa mesa = buscarPorId(id);
        
        if (!mesa.getReservas().isEmpty()) {
            throw new BusinessException("Não é possível remover uma mesa com reservas ativas");
        }
        
        mesaRepository.delete(mesa);
    }
}