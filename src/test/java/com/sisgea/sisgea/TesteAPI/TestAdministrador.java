package com.sisgea.sisgea.TesteAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Models.AdministradorModel;
import com.sisgea.Entidades.Administrador;
import com.sisgea.Entidades.Usuario;

public class TestAdministrador {
    
    @Test
    public void testCreate() {
        Administrador admin = new Administrador();
        Usuario user = new Usuario();
        user.setPermissao(1);
        user.setSenha("123");
        user.setUsuario("admin_teste");
        admin.setAtivo(true);
        admin.setNome("Admin para Teste");
        admin.setUsuario(user);
        
        Request request = new Request();
        String retorno = request.requisicao(admin, "api/administradores", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Administrador encontrado = null;
        List<Administrador> admins = AdministradorModel.listarAdministradores();
        for (Administrador a : admins) {
            if (a.getUsuario().getUsuario().equals("admin_teste")) {
                encontrado = a;
                AdministradorModel.excluirAdministrador(a);
            }
        }
        
        assertTrue(retorno.contains("\"status\":\"sucesso\""), "Status diferente de sucesso: " + retorno);
        assertNotNull(encontrado, "Administrador não encontrado após criação.");
    }

    @Test
    public void testRead() {
        Administrador admin = new Administrador();
        Usuario user = new Usuario();
        user.setPermissao(1);
        user.setSenha("123");
        user.setUsuario("admin_read");
        admin.setAtivo(true);
        admin.setNome("Admin para Leitura");
        admin.setUsuario(user);
        
        Request request = new Request();
        String retorno = request.requisicao(admin, "api/administradores", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Administrador encontrado = null;
        List<Administrador> admins = AdministradorModel.listarAdministradores();
        for (Administrador a : admins) {
            if (a.getUsuario().getUsuario().equals("admin_read")) {
                encontrado = a;
                break;
            }
        }
        
        assertNotNull(encontrado, "Administrador não encontrado na lista após criação.");
        assertEquals("Admin para Leitura", encontrado.getNome(), "Nome incorreto: " + encontrado.getNome());
        
        String respostaGet = request.requisicao(null, "api/administradores/" + encontrado.getId(), "GET");
        assertTrue(respostaGet.contains("Admin para Leitura"), "GET não retornou o administrador correto: " + respostaGet);
        
        AdministradorModel.excluirAdministrador(encontrado);
    }

    @Test
    public void testUpdate() {
        Administrador admin = new Administrador();
        Usuario user = new Usuario();
        user.setPermissao(1);
        user.setSenha("123");
        user.setUsuario("admin_update");
        admin.setAtivo(true);
        admin.setNome("Admin para Update");
        admin.setUsuario(user);
        
        Request request = new Request();
        String retorno = request.requisicao(admin, "api/administradores", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Administrador antigo = null;
        List<Administrador> admins = AdministradorModel.listarAdministradores();
        for (Administrador a : admins) {
            if (a.getUsuario().getUsuario().equals("admin_update")) {
                antigo = a;
                break;
            }
        }
        
        assertNotNull(antigo, "Administrador não encontrado para update.");
        
        antigo.setNome("Admin Atualizado");
        AdministradorModel.atualizarAdministrador(antigo);
        
        Administrador novo = null;
        admins = AdministradorModel.listarAdministradores();
        for (Administrador a : admins) {
            if (a.getUsuario().getUsuario().equals("admin_update")) {
                novo = a;
                break;
            }
        }
        
        assertNotNull(novo, "Administrador não encontrado após update.");
        assertEquals("Admin Atualizado", novo.getNome(), "Nome não foi atualizado: " + novo.getNome());
        
        AdministradorModel.excluirAdministrador(novo);
    }

    @Test
    public void testDelete() {
        Administrador admin = new Administrador();
        Usuario user = new Usuario();
        user.setPermissao(1);
        user.setSenha("123");
        user.setUsuario("admin_delete");
        admin.setAtivo(true);
        admin.setNome("Admin para Delete");
        admin.setUsuario(user);
        
        Request request = new Request();
        String retorno = request.requisicao(admin, "api/administradores", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Administrador encontrado = null;
        List<Administrador> admins = AdministradorModel.listarAdministradores();
        for (Administrador a : admins) {
            if (a.getUsuario().getUsuario().equals("admin_delete")) {
                encontrado = a;
                break;
            }
        }
        
        assertNotNull(encontrado, "Administrador não encontrado para delete.");
        
        AdministradorModel.excluirAdministrador(encontrado);
        
        Administrador checar = null;
        admins = AdministradorModel.listarAdministradores();
        for (Administrador a : admins) {
            if (a.getUsuario().getUsuario().equals("admin_delete")) {
                checar = a;
                break;
            }
        }
        
        assertNull(checar, "Administrador ainda existe após DELETE.");
    }
}