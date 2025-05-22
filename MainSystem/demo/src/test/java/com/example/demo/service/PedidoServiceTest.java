package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.PedidoDTO;
import com.example.demo.Exception.BusinessException;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.*;
import com.example.demo.repository.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;
    
    @Mock
    private ReservaRepository reservaRepository;
    
    @Mock
    private MesaRepository mesaRepository;
    
    @Mock
    private ItemCardapioRepository itemCardapioRepository;
    
    @InjectMocks
    private PedidoService pedidoService;
    
    @Test
    void criarPedido_deveRetornarPedido_quandoDadosValidos() {
        // Arrange
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        
        Mesa mesa = new Mesa();
        mesa.setId(1L);
        reserva.setMesa(mesa);
        
        ItemCardapio itemCardapio = new ItemCardapio();
        itemCardapio.setId(1L);
        itemCardapio.setPreco(BigDecimal.valueOf(10.0));
        
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setReservaId(1L);
        pedidoDTO.setMesaId(1L);
        pedidoDTO.setItemCardapioId(1L);
        pedidoDTO.setQuantidade(2);
        pedidoDTO.setValorTotal(BigDecimal.valueOf(20.0));
        
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));
        when(itemCardapioRepository.findById(1L)).thenReturn(Optional.of(itemCardapio));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido pedido = invocation.getArgument(0);
            pedido.setId(1L);
            return pedido;
        });
        
        // Act
        Pedido resultado = pedidoService.criarPedido(pedidoDTO);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getReserva().getId());
        assertEquals(1L, resultado.getMesa().getId());
        assertEquals(1L, resultado.getItemCardapio().getId());
        assertEquals(2, resultado.getQuantidade());
        assertEquals(BigDecimal.valueOf(20.0), resultado.getValorTotal());
        assertNotNull(resultado.getDataHora());
    }
    
    @Test
    void criarPedido_deveLancarExcecao_quandoReservaNaoEncontrada() {
        // Arrange
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setReservaId(1L);
        
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pedidoService.criarPedido(pedidoDTO);
        });
    }
    
    @Test
    void criarPedido_deveLancarExcecao_quandoMesaNaoCorrespondeAReserva() {
        // Arrange
        Reserva reserva = new Reserva();
        Mesa mesaReserva = new Mesa();
        mesaReserva.setId(1L);
        reserva.setMesa(mesaReserva);
        
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setReservaId(1L);
        pedidoDTO.setMesaId(2L); // ID diferente da mesa da reserva
        
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(mesaRepository.findById(2L)).thenReturn(Optional.of(new Mesa()));
        
        // Act & Assert
        assertThrows(BusinessException.class, () -> {
            pedidoService.criarPedido(pedidoDTO);
        });
    }
    
    @Test
    void criarPedido_deveLancarExcecao_quandoItemCardapioNaoEncontrado() {
        // Arrange
        Reserva reserva = new Reserva();
        Mesa mesa = new Mesa();
        mesa.setId(1L);
        reserva.setMesa(mesa);
        
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setReservaId(1L);
        pedidoDTO.setMesaId(1L);
        pedidoDTO.setItemCardapioId(1L);
        
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));
        when(itemCardapioRepository.findById(1L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pedidoService.criarPedido(pedidoDTO);
        });
    }
    
    @Test
    void buscarPorId_deveRetornarPedido_quandoEncontrado() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        
        // Act
        Pedido resultado = pedidoService.buscarPorId(1L);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }
    
    @Test
    void buscarPorId_deveLancarExcecao_quandoNaoEncontrado() {
        // Arrange
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pedidoService.buscarPorId(1L);
        });
    }
    
    @Test
    void listarPorReserva_deveRetornarListaDePedidos() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        
        when(pedidoRepository.findByReservaId(1L)).thenReturn(Collections.singletonList(pedido));
        
        // Act
        List<Pedido> resultados = pedidoService.listarPorReserva(1L);
        
        // Assert
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(1L, resultados.get(0).getId());
    }
    
    @Test
    void listarPorMesa_deveRetornarListaDePedidos() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        
        when(pedidoRepository.findByMesaId(1L)).thenReturn(Collections.singletonList(pedido));
        
        // Act
        List<Pedido> resultados = pedidoService.listarPorMesa(1L);
        
        // Assert
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(1L, resultados.get(0).getId());
    }
    
    @Test
    void removerPedido_deveDeletarPedido_quandoExistir() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        doNothing().when(pedidoRepository).delete(pedido);
        
        // Act
        pedidoService.removerPedido(1L);
        
        // Assert
        verify(pedidoRepository, times(1)).delete(pedido);
    }
    
    @Test
    void removerPedido_deveLancarExcecao_quandoNaoEncontrado() {
        // Arrange
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pedidoService.removerPedido(1L);
        });
        verify(pedidoRepository, never()).delete(any());
    }
}