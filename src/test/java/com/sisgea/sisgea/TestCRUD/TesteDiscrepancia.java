package com.sisgea.sisgea.TestCRUD;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.DiscrepanciaController;
import com.sisgea.Entidades.Discrepancia;

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
}
