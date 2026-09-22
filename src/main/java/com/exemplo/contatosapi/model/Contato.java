package com.exemplo.contatosapi.model;

/**
 * Classe de modelo (model): representa um Contato dentro do sistema.
 *
 * Ela nao tem nenhuma anotacao especial do Spring porque nao vamos usar banco
 * de dados - e apenas um "molde" com os campos e os metodos getters/setters
 * que o Spring usa para transformar JSON <-> objeto Java automaticamente.
 *
 * Campos: id + 4 campos (nome, telefone, email, categoria, favorito).
 */
public class Contato {

    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String categoria; // ex: "Familia", "Trabalho", "Amigos"
    private boolean favorito; // true/false

    // Construtor vazio: obrigatorio para o Spring conseguir montar o objeto a
    // partir do JSON recebido no corpo da requisicao (@RequestBody).
    public Contato() {
    }

    public Contato(Long id, String nome, String telefone, String email, String categoria, boolean favorito) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.categoria = categoria;
        this.favorito = favorito;
    }

    // Getters e setters: o Spring usa eles para ler/escrever os campos ao
    // converter entre JSON e objeto Java.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}
