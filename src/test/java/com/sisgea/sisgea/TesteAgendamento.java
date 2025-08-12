package com.sisgea.sisgea;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.AgendamentoController;
import com.sisgea.Entidades.Aeronave;
import com.sisgea.Entidades.Agendamento;
import com.sisgea.Entidades.Aluno;
import com.sisgea.Entidades.Instrutor;

class TesteAgendamento {
    @Test
    void testSalvarAgendamento() {
        Aeronave aeronave = new Aeronave();
        Aluno aluno = new Aluno();
        Instrutor instrutor = new Instrutor();
        AgendamentoController.salvarAgendamento(aeronave, aluno, instrutor, "A", "B", "VFR", "Pendente",
                java.sql.Date.valueOf(LocalDate.now()));
        List<Agendamento> lista = AgendamentoController.listarAgendamentos();
        assertFalse(lista.isEmpty(), "Agendamento não salvo.");
    }

    @Test
    void testListarAgendamentos() {
        List<Agendamento> lista = AgendamentoController.listarAgendamentos();
        assertNotNull(lista, "Lista null.");
    }

    @Test
    void testAtualizarAgendamento() {
        Aeronave aeronave = new Aeronave();
        Aluno aluno = new Aluno();
        Instrutor instrutor = new Instrutor();
        AgendamentoController.salvarAgendamento(aeronave, aluno, instrutor, "A1", "B1", "VFR", "Pendente",
                java.sql.Date.valueOf(LocalDate.now()));
        Agendamento ag = null;
        for (Agendamento a : AgendamentoController.listarAgendamentos()) {
            ag = a;
            break;
        }
        assertNotNull(ag, "Agendamento não encontrado.");
        ag.setStatus("Confirmado");
        new AgendamentoController().atualizarAgendamento(ag);
        Agendamento atualizado = null;
        for (Agendamento a : AgendamentoController.listarAgendamentos()) {
            if ("Confirmado".equals(a.getStatus())) {
                atualizado = a;
                break;
            }
        }
        assertNotNull(atualizado, "Agendamento não foi atualizado.");
    }

    @Test
    void testDeletarAgendamento() {
        Aeronave aeronave = new Aeronave();
        Aluno aluno = new Aluno();
        Instrutor instrutor = new Instrutor();
        AgendamentoController.salvarAgendamento(aeronave, aluno, instrutor, "A2", "B2", "VFR", "Pendente",
                java.sql.Date.valueOf(LocalDate.now()));
        Agendamento ag = null;
        for (Agendamento a : AgendamentoController.listarAgendamentos()) {
            ag = a;
            break;
        }
        assertNotNull(ag, "Agendamento para deletar não encontrado.");
        new AgendamentoController().deletarAgendamento(ag);
        boolean existe = false;
        for (Agendamento a : AgendamentoController.listarAgendamentos()) {
            if (a.equals(ag)) {
                existe = true;
                break;
            }
        }
        assertFalse(existe, "Agendamento não foi deletado.");
    }
}
