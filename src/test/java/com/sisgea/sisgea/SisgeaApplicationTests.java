package com.sisgea.sisgea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;

import java.util.List;
import org.junit.jupiter.api.Test;

import com.BancoDados.Conexao;
import com.sisgea.BancoDados.Controllers.UsuarioController;
import com.sisgea.Entidades.Usuario;

class SisgeaApplicationTests {

    @Test
    void testConexaoComBancoDeDados() {
        Connection conexao = Conexao.getConexao();
        assertNotNull(conexao, "A conexão com o banco de dados deve ser estabelecida.");
        try {
            assertFalse(conexao.isClosed(), "A conexão deve estar aberta.");
            conexao.close();
        } catch (Exception e) {
            fail("Falha na conexão: " + e.getMessage());
        }
    }

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
}
