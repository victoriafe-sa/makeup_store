package com.example.makeup_store.controller;

import com.example.makeup_store.model.Produto;
import com.example.makeup_store.repository.PedidoRepository;
import com.example.makeup_store.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private PedidoRepository pedidoRepository;

    // Carrega o painel administrativo com estatísticas e listas
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalPedidos", pedidoRepository.count());
        model.addAttribute("totalProdutos", produtoRepository.count());
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("pedidosRecentes", pedidoRepository.findAll());
        return "admin/dashboard";
    }

    // Salva um novo produto
    @PostMapping("/produtos/salvar")
    public String salvarProduto(Produto produto) {
        produtoRepository.save(produto);
        return "redirect:/admin/dashboard";
    }
    
    // Exibe o formulário de edição para um produto existente
    @GetMapping("/produtos/editar/{id}")
    public String editarProdutoForm(@PathVariable Integer id, Model model) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + id));
        model.addAttribute("produto", produto);
        return "admin/editar-produto";
    }
    
    // Processa a atualização dos dados do produto
    @PostMapping("/produtos/editar/{id}")
    public String atualizarProduto(@PathVariable Integer id, Produto produto) {
        produto.setId(id); // Garante que o ID correto seja mantido
        produtoRepository.save(produto);
        return "redirect:/admin/dashboard";
    }
    
    // Tenta excluir um produto e trata erros de integridade (chave estrangeira)
    @GetMapping("/produtos/excluir/{id}")
    public String excluirProduto(@PathVariable Integer id) {
        try {
            produtoRepository.deleteById(id);
            // Retorna sucesso se conseguir apagar
            return "redirect:/admin/dashboard?sucesso=excluido";
        } catch (Exception e) {
            // Se o produto já foi vendido (está em um Pedido), o banco bloqueia a exclusão.
            // Retornamos um erro para o front-end exibir um alerta.
            return "redirect:/admin/dashboard?erro=vinculado";
        }
    }
}