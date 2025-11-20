package com.example.makeup_store;

import com.example.makeup_store.model.Cliente;
import com.example.makeup_store.model.Produto;
import com.example.makeup_store.repository.ClienteRepository;
import com.example.makeup_store.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(ProdutoRepository produtoRepository, ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (produtoRepository.count() == 0) {
            // Adicionando produtos com URLs de imagens reais
            produtoRepository.save(new Produto(null, "Batom Matte Ruby Woo", 99.00, 50, "https://images.unsplash.com/photo-1586495777744-4413f21062fa?auto=format&fit=crop&w=400&q=80"));
            produtoRepository.save(new Produto(null, "Kit Shampoo + Condicionador + Máscara", 150.00, 30, "https://images.unsplash.com/photo-1631729371254-42c2892f0e6e?auto=format&fit=crop&w=400&q=80"));
            produtoRepository.save(new Produto(null, "Rímel Maybelline", 75.00, 3, "https://images.unsplash.com/photo-1631214524020-7e18db9a8f92?auto=format&fit=crop&w=400&q=80"));
            produtoRepository.save(new Produto(null, "Paleta de Sombras Nude", 180.00, 15, "https://images.unsplash.com/photo-1512496015851-a90fb38ba796?auto=format&fit=crop&w=400&q=80"));
            produtoRepository.save(new Produto(null, "Creme Facial", 45.90, 100, "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?auto=format&fit=crop&w=400&q=80"));
            produtoRepository.save(new Produto(null, "Blush Pêssego", 60.00, 20, "https://images.unsplash.com/photo-1515688594390-b649af70d282?auto=format&fit=crop&w=400&q=80"));
            produtoRepository.save(new Produto(null, "Iluminador em Pó Gold", 89.90, 25, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQuW1ShRbjESwkpuoKyY-XsdmSZ5mvrdJpNKg&s?auto=format&fit=crop&w=400&q=80"));
            produtoRepository.save(new Produto(null, "Kit Pincéis Profissionais", 129.00, 15, "https://images.unsplash.com/photo-1516975080664-ed2fc6a32937?auto=format&fit=crop&w=400&q=80"));
            produtoRepository.save(new Produto(null, "Esponja Blender Soft", 29.90, 200, "https://daniellebeatrizmakeup.com.br/loja/wp-content/uploads/2023/02/esponja-para-maquiagem-soft-blender-feels-ruby-rose.jpg?auto=format&fit=crop&w=400&q=80"));
            produtoRepository.save(new Produto(null, "Sérum Vitamina C", 95.00, 40, "https://images.unsplash.com/photo-1601049541289-9b1b7bbbfe19?auto=format&fit=crop&w=400&q=80"));
            produtoRepository.save(new Produto(null, "Água Micelar Bifásica", 35.50, 60, "https://www.2beauty.com.br/blog/wp-content/uploads/2018/06/demaquilante1.jpg?auto=format&fit=crop&w=400&q=80"));
            produtoRepository.save(new Produto(null, "Gloss Labial Rosé", 49.90, 80, "https://m.media-amazon.com/images/I/71HavgLEtfL._AC_SX679_.jpg?auto=format&fit=crop&w=400&q=80"));
        }

        if (clienteRepository.count() == 0) {
            clienteRepository.save(new Cliente(null, "Administrador", "admin@email.com", passwordEncoder.encode("123456"), "Loja Sede", "ADMIN"));
            clienteRepository.save(new Cliente(null, "Cliente Teste", "user@email.com", passwordEncoder.encode("123456"), "Rua das Flores, 10", "USER"));
        }
    }
}