package com.example.makeup_store.model;

import com.example.makeup_store.EstoqueInsuficienteException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // Diz ao Spring que esta classe é uma tabela no banco
@Data // Lombok: Gera Getters, Setters, toString, etc.
@NoArgsConstructor // Lombok: Construtor vazio (obrigatório para JPA)
@AllArgsConstructor // Lombok: Construtor com todos os campos
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento (1, 2, 3...)
    private Integer id;
    
    private String nome;
    private double preco;
    private int estoque;
    private String imagemUrl; // URL para exibir a foto no front-end
    
    // Construtor customizado sem ID (o banco gera o ID)
    public Produto(String nome, double preco, int estoque, String imagemUrl) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.imagemUrl = imagemUrl;
    }
    
    // Regra de negócio: Tenta diminuir estoque, lança erro se não tiver
    public void diminuirEstoque(int quantidade) throws EstoqueInsuficienteException {
        if (this.estoque < quantidade) {
            throw new EstoqueInsuficienteException("Estoque insuficiente.");
        }
        this.estoque -= quantidade;
    }
}