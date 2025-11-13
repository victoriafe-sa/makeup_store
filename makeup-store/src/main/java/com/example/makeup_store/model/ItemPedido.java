package com.example.makeup_store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ItemPedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
    
    @ManyToOne
    @JsonIgnore // Evita loop infinito na serialização JSON/toString
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    
    private int quantidade;
    private double precoUnitario; // Guarda o preço no momento da compra

    public ItemPedido(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }
    
    public double getPrecoTotal() {
        return precoUnitario * quantidade;
    }
}
