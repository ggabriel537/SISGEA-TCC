package com.sisgea.Entidades;

public class Administrador extends Usuario {
    private String nome;

    public Administrador(String usuario, String senha, Integer permissao, String nome, String cpf, String telefone, String email) {
        super(usuario, senha, permissao);
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
