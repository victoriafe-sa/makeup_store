package com.example.makeup_store.controller;

import com.example.makeup_store.model.Cliente;
import com.example.makeup_store.repository.ClienteRepository;
import com.example.makeup_store.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PerfilController {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/perfil")
    public String visualizarPerfil(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow();
        
        model.addAttribute("cliente", cliente);
        model.addAttribute("pedidos", pedidoRepository.findByCliente(cliente));
        
        return "perfil";
    }

    // NOVO: Processar edição do perfil
    @PostMapping("/perfil/editar")
    public String editarPerfil(@AuthenticationPrincipal UserDetails userDetails, Cliente clienteAtualizado) {
        String email = userDetails.getUsername();
        Cliente clienteExistente = clienteRepository.findByEmail(email).orElseThrow();

        // Atualiza dados básicos
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEndereco(clienteAtualizado.getEndereco());

        // Só atualiza a senha se o usuário digitou algo novo
        if (clienteAtualizado.getSenha() != null && !clienteAtualizado.getSenha().isEmpty()) {
            clienteExistente.setSenha(passwordEncoder.encode(clienteAtualizado.getSenha()));
        }

        clienteRepository.save(clienteExistente);
        return "redirect:/perfil?sucesso";
    }
}