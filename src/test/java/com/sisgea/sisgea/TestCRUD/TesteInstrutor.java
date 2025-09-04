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
import com.sisgea.Entidades.Usuario;

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

        Usuario usuario = new Usuario("usuarioDA1235", "senha123", 1);
        Instrutor instrutor = new Instrutor(usuario, "Instrutor DA123", "111.222.333-44", "12345-6789",
                "instrutora@email.com", null, null);
        instrutor.setEndereco(endereco);

        InstrutorController.salvarInstrutor(instrutor);

        List<Instrutor> lista = InstrutorController.listarInstrutores();
        assertFalse(lista.isEmpty());
        Instrutor salvo = lista.stream()
                .filter(i -> "Instrutor DA123".equals(i.getNome()))
                .findFirst()
                .orElse(null);
        assertNotNull(salvo);
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

        Usuario usuario = new Usuario("usuarioDA1245", "senha123", 1);
        Instrutor instrutor = new Instrutor(usuario, "Instrutor DA124", "999.888.777-66", "11111-2222",
                "instrutorc@email.com", null, null);
        instrutor.setEndereco(endereco);

        InstrutorController.salvarInstrutor(instrutor);

        instrutor.setNome("Instrutor DA124 Atualizado");
        InstrutorController.atualizarInstrutor(instrutor);

        Instrutor atualizado = InstrutorController.listarInstrutores().stream()
                .filter(i -> "Instrutor DA124 Atualizado".equals(i.getNome()))
                .findFirst()
                .orElse(null);
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

        Usuario usuario = new Usuario("usuarioDA1255", "senha123", 1);
        Instrutor instrutor = new Instrutor(usuario, "Instrutor DA125", "999.777.555-44", "94444-4444",
                "instrutord@email.com", null, null);
        instrutor.setEndereco(endereco);

        InstrutorController.salvarInstrutor(instrutor);

        InstrutorController.deletarInstrutor(instrutor.getCpf());

        Instrutor deletado = InstrutorController.listarInstrutores().stream()
                .filter(i -> "Instrutor DA125".equals(i.getNome()))
                .findFirst()
                .orElse(null);
        assertNull(deletado);
    }

    @Test
    void testListarInstrutores() {
        List<Instrutor> lista = InstrutorController.listarInstrutores();
        assertNotNull(lista, "Lista null.");
    }
}
