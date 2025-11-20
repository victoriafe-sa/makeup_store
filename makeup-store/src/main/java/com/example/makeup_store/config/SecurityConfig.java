package com.example.makeup_store.config;

import com.example.makeup_store.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                // URLs públicas (não precisa estar logado)
                .requestMatchers("/", "/produtos/**", "/cadastro", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
                
                // URL restrita apenas para ADMIN
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // Qualquer outra URL exige autenticação
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login") // Página HTML customizada de login
                .successHandler(successHandler) // Lógica para decidir para onde ir após login
                .permitAll()
            )
            .logout((logout) -> logout.logoutSuccessUrl("/").permitAll()) // Logout volta pra home
            
            // Configurações necessárias para o console do H2 funcionar
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    // Define a criptografia de senha (BCrypt é o padrão da indústria)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Conecta o Spring Security com o nosso ClienteRepository
    @Bean
    public UserDetailsService userDetailsService(ClienteRepository clienteRepository) {
        return email -> clienteRepository.findByEmail(email)
                .map(cliente -> User.builder()
                        .username(cliente.getEmail())
                        .password(cliente.getSenha())
                        .roles(cliente.getRole()) // Define se é USER ou ADMIN
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}