package com.sisgea.Entidades;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;
    private String senha;
    private Integer permissao;

    public Usuario(String usuario, String senha, Integer permissao) {
        this.usuario = usuario;
        this.senha = senha;
        this.permissao = permissao;
    }

    public Usuario() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String nomeUsuario) {
        this.usuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getPermissao() {
        return permissao;
    }

    public void setPermissao(Integer permissao) {
        this.permissao = permissao;
    }
}
