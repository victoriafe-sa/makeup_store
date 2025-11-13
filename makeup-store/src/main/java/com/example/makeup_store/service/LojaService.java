package com.example.makeup_store.service;

import com.example.makeup_store.EstoqueInsuficienteException;
import com.example.makeup_store.model.ItemPedido;
import com.example.makeup_store.model.Pedido;
import com.example.makeup_store.model.Produto;
import com.example.makeup_store.repository.PedidoRepository;
import com.example.makeup_store.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class LojaService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public LojaService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    // @Transactional garante que ou tudo funciona, ou nada é salvo no DB
    @Transactional
    public Pedido processarPedido(Pedido pedido, Map<Integer, Integer> itensCarrinho) throws EstoqueInsuficienteException {
        System.out.println("🔄 Processando pedido para " + pedido.getCliente().getNome());

        for (Map.Entry<Integer, Integer> entry : itensCarrinho.entrySet()) {
            Integer produtoId = entry.getKey();
            Integer quantidade = entry.getValue();

            if (quantidade <= 0) continue; // Ignora itens com qtd 0

            // Busca o produto no DB
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + produtoId));

            // Valida o estoque (lança exceção se falhar)
            produto.diminuirEstoque(quantidade);
            
            // Atualiza o produto no DB
            produtoRepository.save(produto);

            // Adiciona o item ao pedido
            pedido.adicionarItem(new ItemPedido(produto, quantidade));
        }

        if (pedido.getItens().isEmpty()) {
            throw new RuntimeException("Não é possível processar um pedido vazio.");
        }

        // Se tudo deu certo, salva o pedido
        pedido.setStatus("PROCESSADO");
        pedido.setTotal(pedido.calcularTotal());
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        System.out.println("✅ Pedido #" + pedidoSalvo.getId() + " processado!");
        return pedidoSalvo;
    }
    
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }
    
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }
}