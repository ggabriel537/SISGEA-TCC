package com.sisgea.sisgea.TestCRUD;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.EnderecoController;
import com.sisgea.Entidades.Endereco;

class TesteEndereco {
    @Test
    void testSalvarEndereco() {
        EnderecoController.salvarEndereco("12345-678", "Rua A", "Bairro X", "", "123", "Cidade Y", "Estado Z");
        List<Endereco> lista = EnderecoController.listarEnderecos();
        assertFalse(lista.isEmpty(), "Endereco não salvo.");
        Endereco endereco = null;
        for (Endereco e : lista) {
            if ("Rua A".equals(e.getLogradouro())) {
                endereco = e;
                break;
            }
        }
        assertNotNull(endereco, "Endereco não encontrado.");
    }

    @Test
    void testListarEnderecos() {
        List<Endereco> lista = EnderecoController.listarEnderecos();
        assertNotNull(lista, "Lista null.");
    }

    @Test
    void testAtualizarEndereco() {
        EnderecoController.salvarEndereco("98765-432", "Rua B", "Bairro Y", "Complemento Y", "456", "Cidade Z",
                "Estado W");
        Endereco endereco = null;
        for (Endereco e : EnderecoController.listarEnderecos()) {
            if ("Rua B".equals(e.getLogradouro())) {
                endereco = e;
                break;
            }
        }
        assertNotNull(endereco, "Endereco para update não encontrado.");
        endereco.setLogradouro("Rua B Atualizada");
        EnderecoController.atualizarEndereco(endereco);
        Endereco atualizado = null;
        for (Endereco e : EnderecoController.listarEnderecos()) {
            if ("Rua B Atualizada".equals(e.getLogradouro())) {
                atualizado = e;
                break;
            }
        }
        assertNotNull(atualizado, "Endereco não foi atualizado.");
    }

    @Test
    void testDeletarEndereco() {
        EnderecoController.salvarEndereco("11223-445", "Rua C", "Bairro Z", "Complemento Z", "789", "Cidade W",
                "Estado V");
        Endereco endereco = null;
        for (Endereco e : EnderecoController.listarEnderecos()) {
            if ("Rua C".equals(e.getLogradouro())) {
                endereco = e;
                break;
            }
        }
        assertNotNull(endereco, "Endereco para deletar não encontrado.");
        EnderecoController.deletarEndereco(endereco);
        Endereco deletado = null;
        for (Endereco e : EnderecoController.listarEnderecos()) {
            if ("Rua C".equals(e.getLogradouro())) {
                deletado = e;
                break;
            }
        }
        assertNull(deletado, "Endereco não foi deletado.");
    }
}
