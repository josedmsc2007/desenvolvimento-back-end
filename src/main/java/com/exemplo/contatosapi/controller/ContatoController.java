package com.exemplo.contatosapi.controller;

import com.exemplo.contatosapi.model.Contato;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Controller: e a camada que recebe as requisicoes HTTP (rotas) e devolve
 * uma resposta. Como o enunciado pede para nao usar banco de dados, os
 * contatos ficam guardados em uma lista em memoria (perdem-se quando a
 * aplicacao e reiniciada).
 *
 * @RestController = @Controller + @ResponseBody: diz ao Spring que os
 * metodos aqui dentro respondem direto com dados (JSON), e nao com paginas
 * HTML.
 *
 * @RequestMapping("/api/contatos") define o prefixo de todas as rotas desta
 * classe. Ou seja, toda URL abaixo comeca com /api/contatos.
 */
@RestController
@RequestMapping("/api/contatos")
public class ContatoController {

    // "Banco de dados" em memoria: uma lista simples.
    private final List<Contato> contatos = new ArrayList<>();

    // Gerador manual de ID. Usamos AtomicLong so para o contador nunca
    // repetir um numero, mas poderia ser um "long" comum tambem.
    private final AtomicLong proximoId = new AtomicLong(1);

    // Construtor: cria alguns contatos de exemplo assim que a aplicacao sobe,
    // so para ja ter dado pronto para testar no Postman.
    public ContatoController() {
        contatos.add(new Contato(proximoId.getAndIncrement(), "Ana Silva", "11 99999-0001", "ana@email.com", "Familia", true));
        contatos.add(new Contato(proximoId.getAndIncrement(), "Bruno Costa", "11 99999-0002", "bruno@email.com", "Trabalho", false));
        contatos.add(new Contato(proximoId.getAndIncrement(), "Carla Souza", "11 99999-0003", "carla@email.com", "Amigos", true));
    }

    /**
     * GET /api/contatos
     * Lista todos os contatos. Aceita filtros opcionais via @RequestParam,
     * que podem ser combinados (usados juntos ou separados):
     *   - nome:      busca por nomes que CONTENHAM o texto (sem diferenciar maiusculas/minusculas)
     *   - categoria: busca por categoria EXATA (ex: Trabalho)
     *   - favorito:  filtra por true ou false
     *
     * Exemplos de uso no navegador/Postman:
     *   GET /api/contatos
     *   GET /api/contatos?categoria=Trabalho
     *   GET /api/contatos?nome=an&favorito=true
     */
    @GetMapping
    public ResponseEntity<List<Contato>> listarTodos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Boolean favorito
    ) {
        List<Contato> resultado = new ArrayList<>(contatos);

        if (nome != null && !nome.isBlank()) {
            String busca = nome.toLowerCase();
            resultado.removeIf(c -> c.getNome() == null || !c.getNome().toLowerCase().contains(busca));
        }

        if (categoria != null && !categoria.isBlank()) {
            resultado.removeIf(c -> c.getCategoria() == null || !c.getCategoria().equalsIgnoreCase(categoria));
        }

        if (favorito != null) {
            resultado.removeIf(c -> c.isFavorito() != favorito);
        }

        return ResponseEntity.ok(resultado);
    }

    /**
     * GET /api/contatos/{id}
     * Busca um unico contato pelo ID enviado na propria URL (@PathVariable).
     * Se nao existir, devolve 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscarPorId(@PathVariable Long id) {
        Optional<Contato> encontrado = contatos.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        return encontrado
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/contatos
     * Cadastra um novo contato. Os dados chegam em formato JSON no corpo da
     * requisicao (@RequestBody), e o Spring converte automaticamente esse
     * JSON para um objeto Contato.
     *
     * O ID e sempre gerado manualmente aqui dentro (ignoramos o que vier no
     * corpo, se vier algum), para nunca duplicar.
     *
     * Exemplo de JSON de entrada:
     * {
     *   "nome": "Daniela Lima",
     *   "telefone": "11 98888-0004",
     *   "email": "dani@email.com",
     *   "categoria": "Trabalho",
     *   "favorito": false
     * }
     */
    @PostMapping
    public ResponseEntity<Contato> cadastrar(@RequestBody Contato novoContato) {
        novoContato.setId(proximoId.getAndIncrement());
        contatos.add(novoContato);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoContato);
    }

    /**
     * PUT /api/contatos/{id}
     * Atualiza um contato existente. O ID vem pela URL (@PathVariable) e os
     * novos dados vem no corpo em JSON (@RequestBody).
     * Se o ID nao existir, devolve 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Contato> atualizar(@PathVariable Long id, @RequestBody Contato dadosAtualizados) {
        for (Contato c : contatos) {
            if (c.getId().equals(id)) {
                c.setNome(dadosAtualizados.getNome());
                c.setTelefone(dadosAtualizados.getTelefone());
                c.setEmail(dadosAtualizados.getEmail());
                c.setCategoria(dadosAtualizados.getCategoria());
                c.setFavorito(dadosAtualizados.isFavorito());
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * DELETE /api/contatos/{id}
     * Remove um contato pelo ID. Se existia e foi removido, devolve 204 No
     * Content (sucesso, sem corpo de resposta). Se nao existia, devolve 404.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean removido = contatos.removeIf(c -> c.getId().equals(id));

        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
