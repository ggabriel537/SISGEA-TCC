package com.sisgea.sisgea.TestCRUD;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.AgendamentoController;
import com.sisgea.Entidades.Aeronave;
import com.sisgea.Entidades.Agendamento;
import com.sisgea.Entidades.Aluno;
import com.sisgea.Entidades.Instrutor;
import com.sisgea.sisgea.Uteis;

class TesteAgendamento {

    

    @Test
    void testSalvarAgendamento() {
        Aeronave aeronave = new Aeronave(Uteis.gerarMatriculaAleatoria(), "Cessna 172", "Cessna", "VFR", "Treinamento");
        Aluno aluno = new Aluno("João Silva", "12345678901", "99999-9999", "joao@email.com", "Aviação");
        aluno.setId(UUID.randomUUID());
        Instrutor instrutor = new Instrutor("instrutor1", "senha", 2, "Carlos Souza", "98765432100", "98888-8888",
                "carlos@email.com");
        Date dataAgendamento = new Date(System.currentTimeMillis());

        AgendamentoController.salvarAgendamento(aeronave, aluno, instrutor, "SBSP", "SBGL", "Duplo", "Agendado",
                dataAgendamento);

        List<Agendamento> lista = AgendamentoController.listarAgendamentos();
        assertFalse(lista.isEmpty(), "Lista vazia! Não foi salvo o agendamento.");

        Agendamento agendamentoSalvo = null;
        for (Agendamento a : lista) {
            if ("SBSP".equals(a.getPartida()) && "SBGL".equals(a.getDestino())) {
                agendamentoSalvo = a;
                break;
            }
        }
        assertNotNull(agendamentoSalvo, "Agendamento salvo não encontrado.");
        assertEquals("Duplo", agendamentoSalvo.getTipo_voo());
        assertEquals("Agendado", agendamentoSalvo.getStatus());
    }

    @Test
    void testListarAgendamentos() {
        List<Agendamento> lista = AgendamentoController.listarAgendamentos();
        assertNotNull(lista, "Lista retornou null.");
    }

    @Test
    void testAtualizarAgendamento() {
        Aeronave aeronave = new Aeronave(Uteis.gerarMatriculaAleatoria(), "Piper PA-28", "Piper", "IFR", "Treinamento");
        Aluno aluno = new Aluno("Maria Oliveira", "11122233344", "97777-7777", "maria@email.com", "Aviação");
        aluno.setId(UUID.randomUUID());
        Instrutor instrutor = new Instrutor("instrutor2", "senha2", 2, "Paulo Lima", "99988877766", "96666-6666",
                "paulo@email.com");
        Date dataAgendamento = new Date(System.currentTimeMillis());

        AgendamentoController.salvarAgendamento(aeronave, aluno, instrutor, "SBGR", "SBKP", "Solo", "Pendente",
                dataAgendamento);

        Agendamento agendamentoParaUpdate = null;
        for (Agendamento a : AgendamentoController.listarAgendamentos()) {
            if ("SBGR".equals(a.getPartida()) && "SBKP".equals(a.getDestino())) {
                agendamentoParaUpdate = a;
                break;
            }
        }
        assertNotNull(agendamentoParaUpdate, "Agendamento para update não encontrado.");

        agendamentoParaUpdate.setStatus("Confirmado");
        new AgendamentoController().atualizarAgendamento(agendamentoParaUpdate);

        Agendamento atualizado = null;
        for (Agendamento a : AgendamentoController.listarAgendamentos()) {
            if (a.getId().equals(agendamentoParaUpdate.getId())) {
                atualizado = a;
                break;
            }
        }
        assertNotNull(atualizado, "Agendamento atualizado não encontrado.");
        assertEquals("Confirmado", atualizado.getStatus(), "Status não foi atualizado.");
    }

    @Test
    void testDeletarAgendamento() {
        Aeronave aeronave = new Aeronave(Uteis.gerarMatriculaAleatoria(), "Embraer 190", "Embraer", "IFR", "Comercial");
        Aluno aluno = new Aluno("Pedro Santos", "22233344455", "95555-5555", "pedro@email.com", "Aviação");
        aluno.setId(UUID.randomUUID());
        Instrutor instrutor = new Instrutor("instrutor3", "senha3", 2, "Lucas Costa", "77766655544", "94444-4444",
                "lucas@email.com");
        Date dataAgendamento = new Date(System.currentTimeMillis());

        AgendamentoController.salvarAgendamento(aeronave, aluno, instrutor, "SBCT", "SBPJ", "Duplo", "Agendado",
                dataAgendamento);

        Agendamento agendamentoParaDeletar = null;
        for (Agendamento a : AgendamentoController.listarAgendamentos()) {
            if ("SBCT".equals(a.getPartida()) && "SBPJ".equals(a.getDestino())) {
                agendamentoParaDeletar = a;
                break;
            }
        }
        assertNotNull(agendamentoParaDeletar, "Agendamento para deletar não encontrado.");

        new AgendamentoController().deletarAgendamento(agendamentoParaDeletar);

        Agendamento deletado = null;
        for (Agendamento a : AgendamentoController.listarAgendamentos()) {
            if (a.getId().equals(agendamentoParaDeletar.getId())) {
                deletado = a;
                break;
            }
        }
        assertNull(deletado, "Agendamento não foi deletado.");
    }
}
