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
@Data // Lombok: cria getters, setters, toString, etc.
@NoArgsConstructor // Lombok: construtor vazio (requerido pelo JPA)
@AllArgsConstructor // Lombok: construtor com todos os campos
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nome;
    private double preco;
    private int estoque;
    
    // Construtor sem ID (para novos produtos)
    public Produto(String nome, double preco, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }
    
    // Métodos de negócio (iguais aos seus)
    public void diminuirEstoque(int quantidade) throws EstoqueInsuficienteException {
        if (this.estoque < quantidade) {
            throw new EstoqueInsuficienteException(
                "Estoque insuficiente para '" + nome + "'. Disponível: " + 
                estoque + ", Solicitado: " + quantidade
            );
        }
        this.estoque -= quantidade;
    }
    
    public void aumentarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.estoque += quantidade;
        }
    }
}
