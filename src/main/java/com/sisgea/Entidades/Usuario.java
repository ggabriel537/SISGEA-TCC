package com.sisgea.Entidades;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    private String usuario;

    private String senha;
    private Integer permissao;

    public Usuario() {
    }

    public Usuario(String usuario, String senha, Integer permissao) {
        PasswordEncoder senhahash = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        this.senha = senhahash.encode(senha);
        this.usuario = usuario;
        this.permissao = permissao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        PasswordEncoder senhahash = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        this.senha = senhahash.encode(senha);
    }

    public Integer getPermissao() {
        return permissao;
    }

    public void setPermissao(Integer permissao) {
        this.permissao = permissao;
    }
}
