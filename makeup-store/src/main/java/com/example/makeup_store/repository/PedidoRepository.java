package com.example.makeup_store.repository;

import com.example.makeup_store.model.Cliente;
import com.example.makeup_store.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    // Busca todos os pedidos de um cliente específico
    List<Pedido> findByCliente(Cliente cliente);
}