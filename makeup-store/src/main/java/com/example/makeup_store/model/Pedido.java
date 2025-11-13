package com.example.makeup_store.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne // Relação: Muitos Pedidos para Um Cliente
    @JoinColumn(name = "cliente_id") // Chave estrangeira
    private Cliente cliente;
    
    // Relação: Um Pedido para Muitos Itens
    // cascade = ALL: Salva/deleta os itens junto com o pedido
    // orphanRemoval = true: Remove itens que não estão mais na lista
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();
    
    private String status;
    private LocalDateTime dataHora;
    private Double total; // Armazenamos o total quando o pedido é fechado

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.status = "ABERTO";
        this.dataHora = LocalDateTime.now();
        this.total = 0.0;
    }
    
    public double calcularTotal() {
        return itens.stream()
                    .mapToDouble(ItemPedido::getPrecoTotal)
                    .sum();
    }
    
    // Método auxiliar para adicionar item e manter a relação bidirecional
    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        item.setPedido(this);
    }
}
