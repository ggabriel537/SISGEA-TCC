package com.sisgea.sisgea.TestCRUD;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.ManutencaoController;
import com.sisgea.Entidades.Manutencao;

class TesteManutencao {
    @Test
    void testSalvarManutencao() {
        ManutencaoController.salvarManutencao("Troca de óleo e filtros", new Date(), "Pendente");
        List<Manutencao> lista = ManutencaoController.listarManutencoes();
        assertFalse(lista.isEmpty(), "Manutencao não salva.");
        Manutencao manutencao = null;
        for (Manutencao m : lista) {
            if ("Troca de óleo e filtros".equals(m.getDescricao()) && "Pendente".equals(m.getStatus())) {
                manutencao = m;
                break;
            }
        }
        assertNotNull(manutencao, "Manutencao não encontrada.");
    }

    @Test
    void testListarManutencoes() {
        List<Manutencao> lista = ManutencaoController.listarManutencoes();
        assertNotNull(lista, "Lista null.");
    }

    @Test
    void testAtualizarManutencao() {
        ManutencaoController.salvarManutencao("Troca de óleo e filtros", new Date(), "Pendente");
        Manutencao manutencaoAtualizada = null;
        for (Manutencao m : ManutencaoController.listarManutencoes()) {
            if ("Troca de óleo e filtros".equals(m.getDescricao()) && "Pendente".equals(m.getStatus())) {
                manutencaoAtualizada = m;
                break;
            }
        }
        assertNotNull(manutencaoAtualizada, "Manutencao para update não encontrada.");
        manutencaoAtualizada.setDescricao("Revisão completa");
        manutencaoAtualizada.setData_est_man(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));
        manutencaoAtualizada.setStatus("Concluída");
        new ManutencaoController().atualizarManutencao(manutencaoAtualizada);
        Manutencao manutencaoVerificada = null;
        for (Manutencao m : ManutencaoController.listarManutencoes()) {
            if ("Revisão completa".equals(m.getDescricao()) && "Concluída".equals(m.getStatus())) {
                manutencaoVerificada = m;
                break;
            }
        }
        assertNotNull(manutencaoVerificada, "Manutencao não foi atualizada.");
    }

    @Test
    void testDeletarManutencao() {
        ManutencaoController.salvarManutencao("Deletar Manutencao", new Date(), "Pendente");
        Manutencao manutencao = null;
        for (Manutencao m : ManutencaoController.listarManutencoes()) {
            if ("Deletar Manutencao".equals(m.getDescricao()) && "Pendente".equals(m.getStatus())) {
                manutencao = m;
                break;
            }
        }
        assertNotNull(manutencao, "Manutencao para deletar não encontrada.");
        new ManutencaoController().deletarManutencao(manutencao);
        Manutencao deletado = null;
        for (Manutencao m : ManutencaoController.listarManutencoes()) {
            if ("Deletar Manutencao".equals(m.getDescricao()) && "Pendente".equals(m.getStatus())) {
                deletado = m;
                break;
            }
        }
        assertNull(deletado, "Manutencao não foi deletada.");
    }
}
