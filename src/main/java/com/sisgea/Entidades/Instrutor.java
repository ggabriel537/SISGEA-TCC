package com.sisgea.Entidades;

public class Instrutor extends Usuario {
    private Integer canac;
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    private String habilitacao;
    private Endereco endereco;

    public Instrutor(String usuario, String senha, Integer permissao, String nome, String cpf, String telefone, String email) {
        super(usuario, senha, permissao);
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public Integer getCanac() {
        return canac;
    }

    public void setCanac(Integer canac) {
        this.canac = canac;
    }
    
    public String getHabilitacao() {
        return habilitacao;
    }

    public void setHabilitacao(String habilitacao) {
        this.habilitacao = habilitacao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
