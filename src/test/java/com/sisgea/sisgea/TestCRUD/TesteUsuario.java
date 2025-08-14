package com.sisgea.sisgea.TestCRUD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.UsuarioController;
import com.sisgea.Entidades.Usuario;

class TesteUsuario{
    @Test
    void testSalvarUsuario() {
        UsuarioController.salvarUsuario("teste", "123456", 1);
        List<Usuario> usuarios = UsuarioController.listarUsuarios();
        assertFalse(usuarios.isEmpty(), "Lista vazia! nao foi salvo o usuário.");
        Usuario usuario = null;
        for (Usuario u : usuarios) {
            if ("teste".equals(u.getUsuario())) {
                usuario = u;
                break;
            }
        }
        assertNotNull(usuario, "DB Puxou users mas não encontrou o usuário inserido.");
        assertEquals("teste", usuario.getUsuario(), "Nome diferente do fornecido.");
    }

    @Test
    void testListarUsuarios() {
        List<Usuario> usuarios = UsuarioController.listarUsuarios();
        assertNotNull(usuarios, "Lista retornou null.");
    }

    @Test
    void testAtualizarUsuario() {
        UsuarioController.salvarUsuario("update", "123", 1);
        Usuario usuario = null;
        for (Usuario u : UsuarioController.listarUsuarios()) {
            if ("update".equals(u.getUsuario())) {
                usuario = u;
                break;
            }
        }
        assertNotNull(usuario, "Usuário para update não encontrado.");
        usuario.setUsuario("updateOK");
        new UsuarioController().atualizarUsuario(usuario);
        Usuario atualizado = null;
        for (Usuario u : UsuarioController.listarUsuarios()) {
            if ("updateOK".equals(u.getUsuario())) {
                atualizado = u;
                break;
            }
        }
        assertNotNull(atualizado, "Usuário não foi atualizado.");
    }

    @Test
    void testDeletarUsuario() {
        UsuarioController.salvarUsuario("delete", "123", 1);
        Usuario usuario = null;
        for (Usuario u : UsuarioController.listarUsuarios()) {
            if ("delete".equals(u.getUsuario())) {
                usuario = u;
                break;
            }
        }
        assertNotNull(usuario, "Usuário para deletar não encontrado.");
        new UsuarioController().deletarUsuario(usuario);
        Usuario deletado = null;
        for (Usuario u : UsuarioController.listarUsuarios()) {
            if ("delete".equals(u.getUsuario())) {
                deletado = u;
                break;
            }
        }
        assertNull(deletado, "Usuário não foi deletado.");
    }
}

