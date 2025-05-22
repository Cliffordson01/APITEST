package com.example.demo.service;

import com.example.demo.dto.ItemCardapioDTO;
import com.example.demo.Exception.BusinessException;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.ItemCardapio;
import com.example.demo.repository.ItemCardapioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemCardapioService {
    private final ItemCardapioRepository itemCardapioRepository;

    public ItemCardapioService(ItemCardapioRepository itemCardapioRepository) {
        this.itemCardapioRepository = itemCardapioRepository;
    }

    @Transactional
    public ItemCardapio criarItem(ItemCardapioDTO itemCardapioDTO) {
        ItemCardapio item = new ItemCardapio();
        item.setNome(itemCardapioDTO.getNome());
        item.setDescricao(itemCardapioDTO.getDescricao());
        item.setPreco(itemCardapioDTO.getPreco());
        
        return itemCardapioRepository.save(item);
    }

    @Transactional(readOnly = true)
    public List<ItemCardapio> listarTodos() {
        return itemCardapioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ItemCardapio buscarPorId(Long id) {
        return itemCardapioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do cardápio não encontrado com ID: " + id));
    }

    @Transactional
    public ItemCardapio atualizarItem(Long id, ItemCardapioDTO itemCardapioDTO) {
        ItemCardapio item = buscarPorId(id);
        item.setNome(itemCardapioDTO.getNome());
        item.setDescricao(itemCardapioDTO.getDescricao());
        item.setPreco(itemCardapioDTO.getPreco());
        
        return itemCardapioRepository.save(item);
    }

    @Transactional
    public void removerItem(Long id) {
        ItemCardapio item = buscarPorId(id);
        
        if (!item.getPedidos().isEmpty()) {
            throw new BusinessException("Não é possível remover um item do cardápio com pedidos associados");
        }
        
        itemCardapioRepository.delete(item);
    }
}