package com.sisgea.Entidades;

import jakarta.persistence.*;

@Entity
public class Instrutor {

    @Column(name = "canac")
    private Integer canac;

    @Id
    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @Column(name = "habilitacao")
    private String habilitacao;

    @OneToOne
    private Endereco endereco;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private Usuario usuario;

    public Instrutor() {
    }

    public Instrutor(Usuario usuario, String nome, String cpf, String telefone, String email, Integer canac, String habilitacao) {
        this.usuario = usuario;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.canac = canac;
        this.habilitacao = habilitacao;
    }

    public Integer getCanac() {
        return canac;
    }

    public void setCanac(Integer canac) {
        this.canac = canac;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
