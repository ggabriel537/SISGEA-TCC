package com.sisgea.sisgea;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.DiscrepanciaController;
import com.sisgea.BancoDados.Controllers.EnderecoController;
import com.sisgea.Entidades.Discrepancia;
import com.sisgea.Entidades.Endereco;

class TesteDiscrepancia {
    @Test
    void testSalvarDiscrepancia() {
        Date hoje = new Date(System.currentTimeMillis());
        DiscrepanciaController.salvarDiscrepancia(hoje, "Sistema Teste", "Descrição Teste", "Correção Teste");
        List<Discrepancia> lista = DiscrepanciaController.listarDiscrepancias();
        assertFalse(lista.isEmpty(), "Discrepancia não salva.");
        Discrepancia discrepancia = null;
        for (Discrepancia d : lista) {
            if ("Descrição Teste".equals(d.getDiscrepancia())) {
                discrepancia = d;
                break;
            }
        }
        assertNotNull(discrepancia, "Discrepancia não encontrada.");
    }

    @Test
    void testListarDiscrepancias() {
        List<Discrepancia> lista = DiscrepanciaController.listarDiscrepancias();
        assertNotNull(lista, "Lista null.");
    }

    @Test
    void testAtualizarDiscrepancia() {
        Date hoje = new Date(System.currentTimeMillis());
        DiscrepanciaController.salvarDiscrepancia(hoje, "Sistema Atualizar", "Descricao Atualizar",
                "Correcao Atualizar");
        Discrepancia discrepancia = null;
        for (Discrepancia d : DiscrepanciaController.listarDiscrepancias()) {
            if ("Descricao Atualizar".equals(d.getDiscrepancia())) {
                discrepancia = d;
                break;
            }
        }
        assertNotNull(discrepancia, "Discrepancia para update não encontrada.");
        discrepancia.setDiscrepancia("Descricao Atualizada OK");
        new DiscrepanciaController().atualizarDiscrepancia(discrepancia);
        Discrepancia atualizado = null;
        for (Discrepancia d : DiscrepanciaController.listarDiscrepancias()) {
            if ("Descricao Atualizada OK".equals(d.getDiscrepancia())) {
                atualizado = d;
                break;
            }
        }
        assertNotNull(atualizado, "Discrepancia não foi atualizada.");
    }

    @Test
    void testDeletarDiscrepancia() {
        Date hoje = new Date(System.currentTimeMillis());
        DiscrepanciaController.salvarDiscrepancia(hoje, "Sistema Deletar", "Descricao Deletar", "Correcao Deletar");
        Discrepancia discrepancia = null;
        for (Discrepancia d : DiscrepanciaController.listarDiscrepancias()) {
            if ("Descricao Deletar".equals(d.getDiscrepancia())) {
                discrepancia = d;
                break;
            }
        }
        assertNotNull(discrepancia, "Discrepancia para deletar não encontrada.");
        new DiscrepanciaController().deletarDiscrepancia(discrepancia);
        Discrepancia deletado = null;
        for (Discrepancia d : DiscrepanciaController.listarDiscrepancias()) {
            if ("Descricao Deletar".equals(d.getDiscrepancia())) {
                deletado = d;
                break;
            }
        }
        assertNull(deletado, "Discrepancia não foi deletada.");
    }

    // --- Endereco ---

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
        new EnderecoController().atualizarEndereco(endereco);
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
        new EnderecoController().deletarEndereco(endereco);
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
