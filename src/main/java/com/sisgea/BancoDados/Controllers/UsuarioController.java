package com.sisgea.BancoDados.Controllers;

import java.util.List;

import com.sisgea.BancoDados.Models.UsuarioModel;
import com.sisgea.Entidades.Usuario;

public class UsuarioController {

    public static void salvarUsuario(String nome, String senha, Integer permissao) {
        Usuario usuario = new Usuario();
        usuario.setUsuario(nome);
        usuario.setSenha(senha);
        usuario.setPermissao(permissao);
        UsuarioModel.salvarUsuario(usuario);
    }

    public static List<Usuario> listarUsuarios() {
        return UsuarioModel.listarUsuarios();
    }

    public void deletarUsuario(Usuario usuario) {
        UsuarioModel.excluirUsuario(usuario);
    }

    public void atualizarItem(Usuario usuario) {
        UsuarioModel.atualizarUsuario(usuario);
    }
}


