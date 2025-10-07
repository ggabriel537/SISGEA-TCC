package com.sisgea.BancoDados.Controllers;

import java.util.List;

import com.sisgea.BancoDados.Models.AdministradorModel;
import com.sisgea.Entidades.Administrador;
import com.sisgea.Entidades.Usuario;

public class AdministradorController {

    public static void salvarAdministrador(String nome, String usuario, String senha, Integer permissao) {
        Usuario user = new Usuario(usuario, senha, permissao);
        Administrador administrador = new Administrador(user, nome);
        administrador.setAtivo(true);
        AdministradorModel.salvarAdministrador(administrador);
    }

    public static void salvarAdministrador(Administrador administrador) {
        administrador.setAtivo(true);
        AdministradorModel.salvarAdministrador(administrador);
    }

    public static List<Administrador> listarAdministradores() {
        return AdministradorModel.listarAdministradores();
    }

    public static Administrador buscarId(String id) {
        return AdministradorModel.buscarAdministrador(id);
    }

    public static void deletarAdministrador(Administrador administrador) {
        administrador.setAtivo(false);
        AdministradorModel.atualizarAdministrador(administrador);
    }

    public static void atualizarAdministrador(Administrador administrador) {
        AdministradorModel.atualizarAdministrador(administrador);
    }
}
