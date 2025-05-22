// Responsavél: Gabriel Ribeiro
package com.example.demo.repository;

import com.example.demo.Model.ItemCardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Long> {
}