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
    
    @ManyToOne // Muitos pedidos podem pertencer a UM cliente
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    // Um pedido tem MUITOS itens
    // cascade = ALL: Se salvar/apagar pedido, salva/apaga itens
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();
    
    private String status;
    private LocalDateTime dataHora;
    private Double total;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.status = "ABERTO";
        this.dataHora = LocalDateTime.now();
        this.total = 0.0;
    }
    
    // Método auxiliar para adicionar item e manter a integridade da relação
    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        item.setPedido(this); // Vincula o item de volta ao pedido
    }

    public double calcularTotal() {
        return itens.stream()
                    .mapToDouble(ItemPedido::getPrecoTotal)
                    .sum();
    }
}