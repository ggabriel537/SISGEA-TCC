package com.sisgea.Entidades;

public class Usuario {
    private String usuario;
    private String senha;
    private Integer permissao;

    public Usuario(String usuario, String senha, Integer permissao) {
        this.usuario = usuario;
        this.senha = senha;
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
        this.senha = senha;
    }

    public Integer getPermissao() {
        return permissao;
    }

    public void setPermissao(Integer permissao) {
        this.permissao = permissao;
    }
}
