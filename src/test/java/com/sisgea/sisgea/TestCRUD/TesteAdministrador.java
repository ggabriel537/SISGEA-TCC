package com.sisgea.sisgea.TestCRUD;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.AdministradorController;
import com.sisgea.Entidades.Administrador;

class TesteAdministrador{
    @Test
    void testSalvarAdministrador() {
        AdministradorController.salvarAdministrador("adm", "admuser", "123", 1);
        List<Administrador> lista = AdministradorController.listarAdministradores();
        assertFalse(lista.isEmpty(), "Lista vazia! Administrador não salvo.");
        Administrador adm = null;
        for (Administrador a : lista) {
            if ("admuser".equals(a.getUsuario())) {
                adm = a;
                break;
            }
        }
        assertNotNull(adm, "Administrador não encontrado.");
    }

    @Test
    void testListarAdministradores() {
        List<Administrador> lista = AdministradorController.listarAdministradores();
        assertNotNull(lista, "Lista null.");
    }

    @Test
    void testAtualizarAdministrador() {
        AdministradorController.salvarAdministrador("adm2", "admuser2", "123", 1);
        Administrador adm = null;
        for (Administrador a : AdministradorController.listarAdministradores()) {
            if ("admuser2".equals(a.getUsuario())) {
                adm = a;
                break;
            }
        }
        assertNotNull(adm, "Administrador não encontrado para update.");
        adm.setNome("adm2OK");
        new AdministradorController().atualizarAdministrador(adm);
        Administrador atualizado = null;
        for (Administrador a : AdministradorController.listarAdministradores()) {
            if ("adm2OK".equals(a.getNome())) {
                atualizado = a;
                break;
            }
        }
        assertNotNull(atualizado, "Administrador não foi atualizado.");
    }

    @Test
    void testDeletarAdministrador() {
        AdministradorController.salvarAdministrador("adm3", "admuser3", "123", 1);
        Administrador adm = null;
        for (Administrador a : AdministradorController.listarAdministradores()) {
            if ("admuser3".equals(a.getUsuario())) {
                adm = a;
                break;
            }
        }
        assertNotNull(adm, "Administrador não encontrado para deletar.");
        new AdministradorController().deletarAdministrador(adm);
        Administrador deletado = null;
        for (Administrador a : AdministradorController.listarAdministradores()) {
            if ("admuser3".equals(a.getUsuario())) {
                deletado = a;
                break;
            }
        }
        assertNull(deletado, "Administrador não foi deletado.");
    }
}

