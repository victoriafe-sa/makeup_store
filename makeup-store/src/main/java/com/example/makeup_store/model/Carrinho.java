package com.example.makeup_store.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@SessionScope
public class Carrinho {
    private List<ItemPedido> itens = new ArrayList<>();

    public void adicionarItem(Produto produto, int quantidade) {
        Optional<ItemPedido> existente = itens.stream()
            .filter(i -> i.getProduto().getId().equals(produto.getId()))
            .findFirst();

        if (existente.isPresent()) {
            ItemPedido item = existente.get();
            item.setQuantidade(item.getQuantidade() + quantidade);
        } else {
            itens.add(new ItemPedido(produto, quantidade));
        }
    }

    // NOVO: Método para atualizar quantidade diretamente
    public void atualizarQuantidade(Integer produtoId, int novaQuantidade) {
        if (novaQuantidade <= 0) {
            removerItem(produtoId);
            return;
        }

        Optional<ItemPedido> existente = itens.stream()
            .filter(i -> i.getProduto().getId().equals(produtoId))
            .findFirst();

        existente.ifPresent(item -> item.setQuantidade(novaQuantidade));
    }

    public void removerItem(Integer produtoId) {
        itens.removeIf(i -> i.getProduto().getId().equals(produtoId));
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void esvaziar() {
        itens.clear();
    }

    public Double getTotal() {
        return itens.stream().mapToDouble(ItemPedido::getPrecoTotal).sum();
    }
    
    public int getQuantidadeTotal() {
        return itens.stream().mapToInt(ItemPedido::getQuantidade).sum();
    }
}