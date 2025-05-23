package com.sisgea.Entidades;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Administrador extends Usuario {

    @Column(name = "nome")
    private String nome;

    public Administrador(String usuario, String senha, Integer permissao, String nome) {
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
