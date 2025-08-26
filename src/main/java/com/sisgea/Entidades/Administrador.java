package com.sisgea.Entidades;

import jakarta.persistence.*;

@Entity
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "nome")
    private String nome;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    public Administrador() {
    }

    public Administrador(Usuario usuario, String nome) {
        this.usuario = usuario;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
