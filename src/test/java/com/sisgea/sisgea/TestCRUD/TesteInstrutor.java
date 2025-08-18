package com.sisgea.sisgea.TestCRUD;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.InstrutorController;
import com.sisgea.BancoDados.Models.EnderecoModel;
import com.sisgea.Entidades.Endereco;
import com.sisgea.Entidades.Instrutor;

class TesteInstrutor {
    @Test
    void testSalvarInstrutor() {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero("100");
        endereco.setBairro("Centro");
        endereco.setCidade("Cidade Teste");
        endereco.setUF("ST");
        EnderecoModel.salvarEndereco(endereco);

        InstrutorController.salvarInstrutor("Instrutor A", "usuarioA", "senha123", 1, 1234, "111.222.333-44","12345-6789", "instrutora@email.com", "Habilitação X", endereco);

        List<Instrutor> lista = InstrutorController.listarInstrutores();
        assertFalse(lista.isEmpty());
        Instrutor instrutor = null;
        for (Instrutor i : lista) {
            if ("Instrutor A".equals(i.getNome())) {
                instrutor = i;
                break;
            }
        }
        assertNotNull(instrutor);
    }

    @Test
    void testListarInstrutores() {
        List<Instrutor> lista = InstrutorController.listarInstrutores();
        assertNotNull(lista, "Lista null.");
    }

    @Test
    void testAtualizarInstrutor() {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Atualizar");
        endereco.setNumero("200");
        endereco.setBairro("Bairro Atualizar");
        endereco.setCidade("Cidade Atualizar");
        endereco.setUF("AT");
        EnderecoModel.salvarEndereco(endereco);

        InstrutorController.salvarInstrutor("Instrutor C", "usuarioC", "senha789", 1, 9012, "999.888.777-66","11111-2222", "instrutorc@email.com", "Habilitação Z", endereco);

        Instrutor instrutor = null;
        for (Instrutor i : InstrutorController.listarInstrutores()) {
            if ("Instrutor C".equals(i.getNome())) {
                instrutor = i;
                break;
            }
        }
        assertNotNull(instrutor);
        instrutor.setNome("Instrutor B Atualizado");
        InstrutorController.atualizarInstrutor(instrutor);
        Instrutor atualizado = null;
        for (Instrutor i : InstrutorController.listarInstrutores()) {
            if ("Instrutor B Atualizado".equals(i.getNome())) {
                atualizado = i;
                break;
            }
        }
        assertNotNull(atualizado);
    }

    @Test
    void testDeletarInstrutor() {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Deletar");
        endereco.setNumero("300");
        endereco.setBairro("Bairro Deletar");
        endereco.setCidade("Cidade Deletar");
        endereco.setUF("DL");
        EnderecoModel.salvarEndereco(endereco);

        InstrutorController.salvarInstrutor("Instrutor C", "usuarioC", "senha789", 1, 9012, "999.888.777-66","11111-2222", "instrutorc@email.com", "Habilitação Z", endereco);

        Instrutor instrutor = null;
        for (Instrutor i : InstrutorController.listarInstrutores()) {
            if ("Instrutor C".equals(i.getNome())) {
                instrutor = i;
                break;
            }
        }
        assertNotNull(instrutor);
        InstrutorController.deletarInstrutor(instrutor);
        Instrutor deletado = null;
        for (Instrutor i : InstrutorController.listarInstrutores()) {
            if ("Instrutor C".equals(i.getNome())) {
                deletado = i;
                break;
            }
        }
        assertNull(deletado);
    }
}
