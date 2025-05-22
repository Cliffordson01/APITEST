package com.example.demo.service;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.Exception.BusinessException;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Cliente criarCliente(ClienteDTO clienteDTO) {
        if (clienteRepository.findByEmail(clienteDTO.getEmail()).isPresent()) {
            throw new BusinessException("Já existe um cliente cadastrado com este e-mail");
        }
        
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        
        return clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
    }

    @Transactional
    public Cliente atualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = buscarPorId(id);
        
        if (!cliente.getEmail().equals(clienteDTO.getEmail())) {
            if (clienteRepository.findByEmail(clienteDTO.getEmail()).isPresent()) {
                throw new BusinessException("Já existe um cliente cadastrado com este e-mail");
            }
        }
        
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void removerCliente(Long id) {
        Cliente cliente = buscarPorId(id);
        
        if (!cliente.getReservas().isEmpty()) {
            throw new BusinessException("Não é possível remover um cliente com reservas ativas");
        }
        
        clienteRepository.delete(cliente);
    }
}