package com.example.demo.service;

import com.example.demo.dto.PedidoDTO;
import com.example.demo.Exception.BusinessException;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ReservaRepository reservaRepository;
    private final MesaRepository mesaRepository;
    private final ItemCardapioRepository itemCardapioRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                        ReservaRepository reservaRepository,
                        MesaRepository mesaRepository,
                        ItemCardapioRepository itemCardapioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.reservaRepository = reservaRepository;
        this.mesaRepository = mesaRepository;
        this.itemCardapioRepository = itemCardapioRepository;
    }

    @Transactional
    public Pedido criarPedido(PedidoDTO pedidoDTO) {
        Reserva reserva = reservaRepository.findById(pedidoDTO.getReservaId())
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));
        
        Mesa mesa = mesaRepository.findById(pedidoDTO.getMesaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mesa não encontrada"));
        
        if (!mesa.getId().equals(reserva.getMesa().getId())) {
            throw new BusinessException("A mesa informada não corresponde à mesa da reserva");
        }
        
        ItemCardapio itemCardapio = itemCardapioRepository.findById(pedidoDTO.getItemCardapioId())
                .orElseThrow(() -> new ResourceNotFoundException("Item do cardápio não encontrado"));
        
        Pedido pedido = new Pedido();
        pedido.setReserva(reserva);
        pedido.setMesa(mesa);
        pedido.setItemCardapio(itemCardapio);
        pedido.setQuantidade(pedidoDTO.getQuantidade());
        pedido.setValorTotal(pedidoDTO.getValorTotal());
        pedido.setDataHora(LocalDateTime.now());
        
        return pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorReserva(Long reservaId) {
        return pedidoRepository.findByReservaId(reservaId);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorMesa(Long mesaId) {
        return pedidoRepository.findByMesaId(mesaId);
    }

    @Transactional
    public void removerPedido(Long id) {
        Pedido pedido = buscarPorId(id);
        pedidoRepository.delete(pedido);
    }
}