package com.example.makeup_store.repository;

import com.example.makeup_store.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    // Podemos criar métodos customizados, ex:
    List<Pedido> findByStatus(String status);
}