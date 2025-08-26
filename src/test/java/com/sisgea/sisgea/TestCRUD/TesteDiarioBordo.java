package com.sisgea.sisgea.TestCRUD;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.DiarioBordoController;
import com.sisgea.Entidades.DiarioBordo;
import com.sisgea.sisgea.Uteis;

class TesteDiarioBordo {

    @Test
    void testSalvarDiarioBordo() {
        String aeronaveId = Uteis.gerarMatriculaAleatoria();

        DiarioBordoController.salvarDiarioBordo(
                aeronaveId, 1, new Date(System.currentTimeMillis()), "AlunoFunc", "InstrutorFunc",
                10f, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 
                "Origem", "Destino", new Date(System.currentTimeMillis()), 5f, 3f, 4f, 2f, 1f, 
                100f, 3, 2, 200f, "Nat", "Ocorrencias");

        List<DiarioBordo> lista = DiarioBordoController.listarDiariosBordo();
        assertFalse(lista.isEmpty(), "Diario de bordo não foi salvo.");

        DiarioBordo encontrado = lista.stream()
                .filter(d -> d.getAeronaveId().equals(aeronaveId) && d.getNroDiario() == 1)
                .findFirst()
                .orElse(null);

        assertNotNull(encontrado, "Diario de bordo não encontrado.");
    }

    @Test
    void testListarDiariosBordo() {
        List<DiarioBordo> lista = DiarioBordoController.listarDiariosBordo();
        assertNotNull(lista, "Lista null.");
    }

    @Test
    void testAtualizarDiarioBordo() {
        String aeronaveId = Uteis.gerarMatriculaAleatoria();

        DiarioBordoController.salvarDiarioBordo(
                aeronaveId, 2, new Date(System.currentTimeMillis()), "AlunoFunc2", "InstrutorFunc2",
                20f, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 
                "Origem2", "Destino2", new Date(System.currentTimeMillis()), 6f, 4f, 5f, 3f, 2f, 
                150f, 4, 3, 250f, "Nat2", "Ocorrencias2");

        DiarioBordo diarioBordo = DiarioBordoController.listarDiariosBordo().stream()
                .filter(d -> d.getAeronaveId().equals(aeronaveId) && d.getNroDiario() == 2)
                .findFirst()
                .orElse(null);

        assertNotNull(diarioBordo, "Diario para update não encontrado.");

        diarioBordo.setFuncaoAluno("AlunoFuncAtualizado");
        DiarioBordoController.atualizarDiarioBordo(diarioBordo);

        DiarioBordo atualizado = DiarioBordoController.listarDiariosBordo().stream()
                .filter(d -> "AlunoFuncAtualizado".equals(d.getFuncaoAluno()))
                .findFirst()
                .orElse(null);

        assertNotNull(atualizado, "Diario não foi atualizado.");
    }

    @Test
    void testDeletarDiarioBordo() {
        String aeronaveId = Uteis.gerarMatriculaAleatoria();

        DiarioBordoController.salvarDiarioBordo(
                aeronaveId, 3, new Date(System.currentTimeMillis()), "AlunoFunc3", "InstrutorFunc3",
                30f, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 
                "Origem3", "Destino3", new Date(System.currentTimeMillis()), 7f, 5f, 6f, 4f, 3f, 
                200f, 5, 4, 300f, "Nat3", "Ocorrencias3");

        DiarioBordo diarioBordo = DiarioBordoController.listarDiariosBordo().stream()
                .filter(d -> d.getAeronaveId().equals(aeronaveId) && d.getNroDiario() == 3)
                .findFirst()
                .orElse(null);

        assertNotNull(diarioBordo, "Diario para deletar não encontrado.");

        DiarioBordoController.deletarDiarioBordo(diarioBordo.getId().toString());

        DiarioBordo deletado = DiarioBordoController.listarDiariosBordo().stream()
                .filter(d -> d.getId().equals(diarioBordo.getId()))
                .findFirst()
                .orElse(null);

        assertNull(deletado, "Diario não foi deletado.");
    }
}
