package com.example.makeup_store.model;

import com.example.makeup_store.EstoqueInsuficienteException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nome;
    private double preco;
    private int estoque;
    private String imagemUrl; // Novo campo para a URL da imagem
    
    // Atualizando o construtor auxiliar
    public Produto(String nome, double preco, int estoque, String imagemUrl) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.imagemUrl = imagemUrl;
    }
    
    public void diminuirEstoque(int quantidade) throws EstoqueInsuficienteException {
        if (this.estoque < quantidade) {
            throw new EstoqueInsuficienteException("Estoque insuficiente.");
        }
        this.estoque -= quantidade;
    }
}