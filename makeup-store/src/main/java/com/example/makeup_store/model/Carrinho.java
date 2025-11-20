package com.example.makeup_store.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@SessionScope // CRÍTICO: Cria uma instância única por usuário/navegador.
              // Os dados persistem enquanto a aba estiver aberta.
public class Carrinho {
    private List<ItemPedido> itens = new ArrayList<>();

    public void adicionarItem(Produto produto, int quantidade) {
        // Verifica se o produto já está no carrinho
        Optional<ItemPedido> existente = itens.stream()
            .filter(i -> i.getProduto().getId().equals(produto.getId()))
            .findFirst();

        if (existente.isPresent()) {
            // Se existe, apenas soma a quantidade
            ItemPedido item = existente.get();
            item.setQuantidade(item.getQuantidade() + quantidade);
        } else {
            // Se não, cria novo item
            itens.add(new ItemPedido(produto, quantidade));
        }
    }

    public void atualizarQuantidade(Integer produtoId, int novaQuantidade) {
        if (novaQuantidade <= 0) {
            removerItem(produtoId);
            return;
        }
        // Busca e atualiza
        itens.stream()
            .filter(i -> i.getProduto().getId().equals(produtoId))
            .findFirst()
            .ifPresent(item -> item.setQuantidade(novaQuantidade));
    }

    public void removerItem(Integer produtoId) {
        itens.removeIf(i -> i.getProduto().getId().equals(produtoId));
    }

    // Cálculos de totais usando Java Streams
    public Double getTotal() {
        return itens.stream().mapToDouble(ItemPedido::getPrecoTotal).sum();
    }
    
    public int getQuantidadeTotal() {
        return itens.stream().mapToInt(ItemPedido::getQuantidade).sum();
    }
    
    public List<ItemPedido> getItens() {
        return itens;
    }

    public void esvaziar() {
        itens.clear();
    }
}