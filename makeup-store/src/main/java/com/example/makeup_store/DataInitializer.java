package com.example.makeup_store;

import com.example.makeup_store.model.Cliente;
import com.example.makeup_store.model.Produto;
import com.example.makeup_store.repository.ClienteRepository;
import com.example.makeup_store.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    // Injeta os repositórios para salvar dados
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    public DataInitializer(ProdutoRepository produtoRepository, ClienteRepository clienteRepository) {
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("📦 CADASTRANDO PRODUTOS INICIAIS...");
        
        // Cadastra produtos se o banco estiver vazio
        if (produtoRepository.count() == 0) {
            produtoRepository.save(new Produto(null, "Batom Matte Ruby Woo", 99.00, 50));
            produtoRepository.save(new Produto(null, "Base Líquida MAC", 250.00, 30));
            produtoRepository.save(new Produto(null, "Rímel Maybelline", 75.00, 3)); // Estoque baixo
            produtoRepository.save(new Produto(null, "Paleta de Sombras Nude", 180.00, 15));
        }

        System.out.println("👥 CADASTRANDO CLIENTES INICIAIS...");
        if (clienteRepository.count() == 0) {
            clienteRepository.save(new Cliente(null, "Ana Silva", "ana@email.com", "Rua A, 123"));
            clienteRepository.save(new Cliente(null, "Carlos Souza", "carlos@email.com", "Av. B, 456"));
        }
        
        System.out.println("✅ Banco de dados inicializado!");
    }
}
