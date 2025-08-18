package com.sisgea.BancoDados.Controllers;

import java.util.List;

import com.sisgea.BancoDados.Models.AdministradorModel;
import com.sisgea.Entidades.Administrador;

public class AdministradorController {
    public static void salvarAdministrador(String nome, String usuario, String senha, Integer permissao) {
        Administrador administrador = new Administrador(usuario, senha, permissao, nome);
        AdministradorModel.salvarAdministrador(administrador);
    }

    public static void salvarAdministrador(Administrador administrador) {
        AdministradorModel.salvarAdministrador(administrador);
    }

    public static List<Administrador> listarAdministradores() {
        return AdministradorModel.listarAdministradores();
    }

    public static Administrador buscarId(String id) {
        return AdministradorModel.buscarAdministrador(id);
    }

    public static void deletarAdministrador(Administrador administrador) {
        AdministradorModel.excluirAdministrador(administrador);
    }

    public static void atualizarAdministrador(Administrador administrador) {
        AdministradorModel.atualizarAdministrador(administrador);
    }
}
