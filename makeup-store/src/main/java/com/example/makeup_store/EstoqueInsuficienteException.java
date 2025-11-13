package com.example.makeup_store;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Quando esta exceção ocorre, retorna um erro 400 (Bad Request)
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EstoqueInsuficienteException extends Exception {
    
    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }
}