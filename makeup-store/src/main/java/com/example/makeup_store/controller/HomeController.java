package com.example.makeup_store.controller;

import com.example.makeup_store.model.Cliente;
import com.example.makeup_store.repository.ClienteRepository;
import com.example.makeup_store.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(Model model) {
        // Pega os 4 primeiros produtos para destaque
        model.addAttribute("destaques", produtoRepository.findAll().stream().limit(4).toList());
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastroForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(Cliente cliente) {
        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
        cliente.setRole("USER");
        clienteRepository.save(cliente);
        return "redirect:/login?cadastrado";
    }
    
    // Substitui o LojaController antigo para produtos
    @GetMapping("/produtos")
    public String listarProdutos(@RequestParam(required = false) String busca, Model model) {
        if (busca != null && !busca.isEmpty()) {
            // Simples filtro em memória (ideal seria query no DB)
            model.addAttribute("produtos", produtoRepository.findAll().stream()
                .filter(p -> p.getNome().toLowerCase().contains(busca.toLowerCase()))
                .toList());
        } else {
            model.addAttribute("produtos", produtoRepository.findAll());
        }
        return "produtos";
    }
}