package com.example.makeup_store.controller;

import com.example.makeup_store.model.*;
import com.example.makeup_store.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired private Carrinho carrinho;
    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private PedidoRepository pedidoRepository;

    @GetMapping
    public String verCarrinho(Model model) {
        model.addAttribute("itens", carrinho.getItens());
        model.addAttribute("total", carrinho.getTotal());
        return "carrinho";
    }

    @PostMapping("/adicionar/{id}")
    public String adicionar(@PathVariable Integer id, @RequestParam int quantidade, HttpServletRequest request) {
        produtoRepository.findById(id).ifPresent(produto -> {
            carrinho.adicionarItem(produto, quantidade);
        });
        
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        }
        return "redirect:/produtos";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarQuantidade(@PathVariable Integer id, @RequestParam int quantidade) {
        carrinho.atualizarQuantidade(id, quantidade);
        return "redirect:/carrinho";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Integer id) {
        carrinho.removerItem(id);
        return "redirect:/carrinho";
    }

    @PostMapping("/finalizar")
    public String finalizar(@AuthenticationPrincipal UserDetails userDetails, 
                            @RequestParam String frete, Model model) {
        if (carrinho.getItens().isEmpty()) {
            return "redirect:/carrinho";
        }

        // --- NOVA VALIDAÇÃO DE ESTOQUE ---
        for (ItemPedido item : carrinho.getItens()) {
            // Busca o produto atualizado no banco para checar o estoque real
            Produto produtoNoBanco = produtoRepository.findById(item.getProduto().getId()).orElse(null);
            
            if (produtoNoBanco == null || produtoNoBanco.getEstoque() < item.getQuantidade()) {
                int estoqueDisponivel = (produtoNoBanco != null) ? produtoNoBanco.getEstoque() : 0;
                
                // Adiciona mensagem de erro e recarrega a página do carrinho
                model.addAttribute("erro", "Estoque insuficiente para: " + item.getProduto().getNome() + 
                                           ". Apenas " + estoqueDisponivel + " unidades disponíveis.");
                model.addAttribute("itens", carrinho.getItens());
                model.addAttribute("total", carrinho.getTotal());
                return "carrinho"; // Retorna para a view do carrinho em vez de redirecionar
            }
        }
        // ---------------------------------

        Cliente cliente = clienteRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus("PROCESSADO");
        
        List<ItemPedido> itensPedido = new ArrayList<>();
        for(ItemPedido itemCarrinho : carrinho.getItens()) {
            ItemPedido novoItem = new ItemPedido(itemCarrinho.getProduto(), itemCarrinho.getQuantidade());
            novoItem.setPedido(pedido);
            itensPedido.add(novoItem);
            
            Produto p = itemCarrinho.getProduto();
            p.setEstoque(p.getEstoque() - itemCarrinho.getQuantidade());
            produtoRepository.save(p);
        }
        
        pedido.setItens(itensPedido);
        pedido.setTotal(carrinho.getTotal());
        pedidoRepository.save(pedido);
        
        carrinho.esvaziar();
        model.addAttribute("pedido", pedido);
        model.addAttribute("freteTipo", frete);
        return "finalizacao";
    }
}