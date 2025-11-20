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

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalPedidos", pedidoRepository.count());
        model.addAttribute("totalProdutos", produtoRepository.count());
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("pedidosRecentes", pedidoRepository.findAll());
        return "admin/dashboard";
    }

    @PostMapping("/produtos/salvar")
    public String salvarProduto(Produto produto) {
        produtoRepository.save(produto);
        return "redirect:/admin/dashboard";
    }
    
    // NOVO: Tela de Edição
    @GetMapping("/produtos/editar/{id}")
    public String editarProdutoForm(@PathVariable Integer id, Model model) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + id));
        model.addAttribute("produto", produto);
        return "admin/editar-produto";
    }
    
    // NOVO: Salvar Edição
    @PostMapping("/produtos/editar/{id}")
    public String atualizarProduto(@PathVariable Integer id, Produto produto) {
        produto.setId(id); // Garante que estamos atualizando o ID correto
        produtoRepository.save(produto);
        return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/produtos/excluir/{id}")
    public String excluirProduto(@PathVariable Integer id) {
        produtoRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }
}