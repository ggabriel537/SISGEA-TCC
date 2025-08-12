package com.sisgea.sisgea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import com.BancoDados.Conexao;

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
}
