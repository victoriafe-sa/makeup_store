package com.example.makeup_store.controller;

import com.example.makeup_store.EstoqueInsuficienteException;
import com.example.makeup_store.model.Cliente;
import com.example.makeup_store.model.Pedido;
import com.example.makeup_store.repository.ClienteRepository;
import com.example.makeup_store.service.LojaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LojaController {

    private final LojaService lojaService;
    private final ClienteRepository clienteRepository;

    public LojaController(LojaService lojaService, ClienteRepository clienteRepository) {
        this.lojaService = lojaService;
        this.clienteRepository = clienteRepository;
    }

    /**
     * Página inicial - Mostra produtos e clientes
     */
    @GetMapping("/")
    public String index(Model model) {
        // Adiciona dados ao 'Model' para o Thymeleaf usar
        model.addAttribute("produtos", lojaService.listarProdutos());
        model.addAttribute("clientes", clienteRepository.findAll());
        return "index"; // Retorna o nome do arquivo HTML (index.html)
    }

    /**
     * Processa a criação de um pedido
     */
    @PostMapping("/criar-pedido")
    public String criarPedido(@RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
        
        try {
            // Pega o ID do cliente do formulário
            Integer clienteId = Integer.parseInt(allParams.get("clienteId"));
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            // Mapeia os produtos e quantidades
            // ex: "qty_1" -> 1, "qty_2" -> 0, ...
            Map<Integer, Integer> itensCarrinho = allParams.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("qty_"))
                    .collect(Collectors.toMap(
                            entry -> Integer.parseInt(entry.getKey().replace("qty_", "")),
                            entry -> Integer.parseInt(entry.getValue().isEmpty() ? "0" : entry.getValue())
                    ));

            Pedido novoPedido = new Pedido(cliente);
            Pedido pedidoProcessado = lojaService.processarPedido(novoPedido, itensCarrinho);

            // Adiciona mensagem de sucesso para a próxima página
            redirectAttributes.addFlashAttribute("sucesso", 
                "Pedido #" + pedidoProcessado.getId() + " criado com sucesso!");

        } catch (EstoqueInsuficienteException e) {
            redirectAttributes.addFlashAttribute("erro", 
                "Falha no pedido: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", 
                "Erro inesperado: " + e.getMessage());
        }

        return "redirect:/"; // Redireciona de volta para a página inicial
    }
    
    /**
     * Página de Relatório de Pedidos
     */
     @GetMapping("/pedidos")
     public String relatorioPedidos(Model model) {
         model.addAttribute("pedidos", lojaService.listarPedidos());
         return "pedidos"; // Retorna o arquivo pedidos.html
     }
}
