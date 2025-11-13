package com.example.makeup_store.repository;

import com.example.makeup_store.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    // Spring Data JPA cria os métodos:
    // save(), findById(), findAll(), delete(), count(), ...
}
