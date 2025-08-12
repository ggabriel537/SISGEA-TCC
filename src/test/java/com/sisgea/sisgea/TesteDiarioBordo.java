package com.sisgea.sisgea;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.DiarioBordoController;
import com.sisgea.Entidades.Agendamento;
import com.sisgea.Entidades.DiarioBordo;
import com.sisgea.Entidades.Discrepancia;

class TesteDiarioBordo {
    @Test
    void testSalvarDiarioBordo() {
        UUID id = UUID.randomUUID();
        String aeronaveId = "PT-AAA";
        Integer nroDiario = 1;
        Date data = new Date(System.currentTimeMillis());
        String funcaoAluno = "AlunoFunc";
        String funcaoInstrutor = "InstrutorFunc";
        Float horaAeronave = 10f;
        Date dataDecolagem = new Date(System.currentTimeMillis());
        Date dataPouso = new Date(System.currentTimeMillis());
        String localDecolagem = "Origem";
        String localPouso = "Destino";
        Date dataCorte = new Date(System.currentTimeMillis());
        Float horasDiu = 5f;
        Float horasNot = 3f;
        Float horasVfr = 4f;
        Float horasIfr = 2f;
        Float horasIfrC = 1f;
        Float combustivelUtilizado = 100f;
        Integer ciclos = 3;
        Integer pob = 2;
        Float carga = 200f;
        String nat = "Nat";
        String ocorrencias = "Ocorrencias";
        List<Discrepancia> discrepancias = new ArrayList<>();
        Agendamento agendamento = new Agendamento();

        DiarioBordoController.salvarDiarioBordo(
                id, aeronaveId, nroDiario, data, funcaoAluno, funcaoInstrutor, horaAeronave,
                dataDecolagem, dataPouso, localDecolagem, localPouso, dataCorte,
                horasDiu, horasNot, horasVfr, horasIfr, horasIfrC, combustivelUtilizado,
                ciclos, pob, carga, nat, ocorrencias, discrepancias, agendamento);

        List<DiarioBordo> lista = DiarioBordoController.listarDiariosBordo();
        assertFalse(lista.isEmpty(), "Diario de bordo não foi salvo.");

        DiarioBordo encontrado = null;
        for (DiarioBordo d : lista) {
            if (d.getId().equals(id)) {
                encontrado = d;
                break;
            }
        }
        assertNotNull(encontrado, "Diario de bordo não encontrado.");
    }

    @Test
    void testListarDiariosBordo() {
        List<DiarioBordo> lista = DiarioBordoController.listarDiariosBordo();
        assertNotNull(lista, "Lista null.");
    }

    @Test
    void testAtualizarDiarioBordo() {
        UUID id = UUID.randomUUID();
        String aeronaveId = "PT-BBB";
        Agendamento agendamento = new Agendamento();
        List<Discrepancia> discrepancias = new ArrayList<>();

        DiarioBordoController.salvarDiarioBordo(
                id, aeronaveId, 2, new Date(System.currentTimeMillis()), "AlunoFunc2", "InstrutorFunc2",
                20f, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "Origem2", "Destino2",
                new Date(System.currentTimeMillis()), 6f, 4f, 5f, 3f, 2f, 150f, 4, 3, 250f, "Nat2", "Ocorrencias2",
                discrepancias, agendamento);

        DiarioBordo diarioBordo = null;
        for (DiarioBordo d : DiarioBordoController.listarDiariosBordo()) {
            if (id.equals(d.getId())) {
                diarioBordo = d;
                break;
            }
        }
        assertNotNull(diarioBordo, "Diario para update não encontrado.");

        diarioBordo.setFuncaoAluno("AlunoFuncAtualizado");
        new DiarioBordoController().atualizarDiarioBordo(diarioBordo);

        DiarioBordo atualizado = null;
        for (DiarioBordo d : DiarioBordoController.listarDiariosBordo()) {
            if ("AlunoFuncAtualizado".equals(d.getFuncaoAluno())) {
                atualizado = d;
                break;
            }
        }
        assertNotNull(atualizado, "Diario não foi atualizado.");
    }

    @Test
    void testDeletarDiarioBordo() {
        UUID id = UUID.randomUUID();
        String aeronaveId = "PT-CCC";
        Agendamento agendamento = new Agendamento();
        List<Discrepancia> discrepancias = new ArrayList<>();

        DiarioBordoController.salvarDiarioBordo(
                id, aeronaveId, 3, new Date(System.currentTimeMillis()), "AlunoFunc3", "InstrutorFunc3",
                30f, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "Origem3", "Destino3",
                new Date(System.currentTimeMillis()), 7f, 5f, 6f, 4f, 3f, 200f, 5, 4, 300f, "Nat3", "Ocorrencias3",
                discrepancias, agendamento);

        DiarioBordo diarioBordo = null;
        for (DiarioBordo d : DiarioBordoController.listarDiariosBordo()) {
            if (id.equals(d.getId())) {
                diarioBordo = d;
                break;
            }
        }
        assertNotNull(diarioBordo, "Diario para deletar não encontrado.");

        new DiarioBordoController().deletarDiarioBordo(diarioBordo);

        DiarioBordo deletado = null;
        for (DiarioBordo d : DiarioBordoController.listarDiariosBordo()) {
            if (id.equals(d.getId())) {
                deletado = d;
                break;
            }
        }
        assertNull(deletado, "Diario não foi deletado.");
    }
}
