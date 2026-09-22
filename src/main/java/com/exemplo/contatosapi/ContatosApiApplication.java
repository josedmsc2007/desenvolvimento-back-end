package com.exemplo.contatosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal: e o "botao liga" da aplicacao.
 * Ao rodar o metodo main, o Spring Boot sobe um servidor web (Tomcat embutido)
 * na porta 8080 e deixa nossa API pronta para receber requisicoes.
 */
@SpringBootApplication
public class ContatosApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContatosApiApplication.class, args);
    }
}
