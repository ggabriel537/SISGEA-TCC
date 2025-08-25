package com.sisgea.sisgea.TestCRUD;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.AgendamentoController;
import com.sisgea.Entidades.Aeronave;
import com.sisgea.Entidades.Agendamento;
import com.sisgea.Entidades.Aluno;
import com.sisgea.Entidades.Instrutor;
import com.sisgea.Entidades.Usuario;
import com.sisgea.sisgea.Uteis;

class TesteAgendamento {

    @Test
    void testSalvarAgendamento() {
        Aeronave aeronave = new Aeronave(Uteis.gerarMatriculaAleatoria(), "Cessna 172", "Cessna", "VFR", "Treinamento");
        Usuario usuarioInstrutor = new Usuario("instrutor1", "senha", 2);
        Aluno aluno = new Aluno("João Silva", "12345678901", "99999-9999", "joao@email.com", "Aviação");
        Instrutor instrutor = new Instrutor(usuarioInstrutor, "Carlos Souza", "98765432100", "98888-8888", "carlos@email.com", 2, "IFR");
        Date dataAgendamento = new Date();
        java.sql.Date dataAgendamentoSql = new java.sql.Date(dataAgendamento.getTime());

        AgendamentoController.salvarAgendamento(aeronave, aluno, instrutor, "SBSP", "SBGL", "Duplo", "Agendado", dataAgendamentoSql);

        List<Agendamento> lista = AgendamentoController.listarAgendamentos();
        assertFalse(lista.isEmpty(), "Lista vazia! Não foi salvo o agendamento.");

        Agendamento agendamentoSalvo = lista.stream()
                .filter(a -> "SBSP".equals(a.getPartida()) && "SBGL".equals(a.getDestino()))
                .findFirst()
                .orElse(null);

        assertNotNull(agendamentoSalvo, "Agendamento salvo não encontrado.");
        assertEquals("Duplo", agendamentoSalvo.getTipo_voo());
        assertEquals("Agendado", agendamentoSalvo.getStatus());
    }

    @Test
    void testAtualizarAgendamento() {
        Aeronave aeronave = new Aeronave(Uteis.gerarMatriculaAleatoria(), "Piper PA-28", "Piper", "IFR", "Treinamento");
        Usuario usuarioInstrutor = new Usuario("instrutor2", "senha2", 2);
        Aluno aluno = new Aluno("Maria Oliveira", "11122233344", "97777-7777", "maria@email.com", "Aviação");
        Instrutor instrutor = new Instrutor(usuarioInstrutor, "Paulo Lima", "99988877766", "96666-6666", "paulo@email.com", 2, "IFR");
        Date dataAgendamento = new Date();
        java.sql.Date dataAgendamentoSql = new java.sql.Date(dataAgendamento.getTime());

        AgendamentoController.salvarAgendamento(aeronave, aluno, instrutor, "SBGR", "SBKP", "Solo", "Pendente", dataAgendamentoSql);

        Agendamento agendamentoParaUpdate = AgendamentoController.listarAgendamentos().stream()
                .filter(a -> "SBGR".equals(a.getPartida()) && "SBKP".equals(a.getDestino()))
                .findFirst()
                .orElse(null);

        assertNotNull(agendamentoParaUpdate, "Agendamento para update não encontrado.");

        agendamentoParaUpdate.setStatus("Confirmado");
        AgendamentoController.atualizarAgendamento(agendamentoParaUpdate);

        Agendamento atualizado = AgendamentoController.listarAgendamentos().stream()
                .filter(a -> a.getId().equals(agendamentoParaUpdate.getId()))
                .findFirst()
                .orElse(null);

        assertNotNull(atualizado, "Agendamento atualizado não encontrado.");
        assertEquals("Confirmado", atualizado.getStatus(), "Status não foi atualizado.");
    }

    @Test
    void testDeletarAgendamento() {
        Aeronave aeronave = new Aeronave(Uteis.gerarMatriculaAleatoria(), "Embraer 190", "Embraer", "IFR", "Comercial");
        Usuario usuarioInstrutor = new Usuario("instrutor3", "senha3", 2);
        Aluno aluno = new Aluno("Pedro Santos", "22233344455", "95555-5555", "pedro@email.com", "Aviação");
        Instrutor instrutor = new Instrutor(usuarioInstrutor, "Lucas Costa", "77766655544", "94444-4444", "lucas@email.com", 2, "IFR");
        Date dataAgendamento = new Date();
        java.sql.Date dataAgendamentoSql = new java.sql.Date(dataAgendamento.getTime());

        AgendamentoController.salvarAgendamento(aeronave, aluno, instrutor, "SBCT", "SBPJ", "Duplo", "Agendado", dataAgendamentoSql);

        Agendamento agendamentoParaDeletar = AgendamentoController.listarAgendamentos().stream()
                .filter(a -> "SBCT".equals(a.getPartida()) && "SBPJ".equals(a.getDestino()))
                .findFirst()
                .orElse(null);

        assertNotNull(agendamentoParaDeletar, "Agendamento para deletar não encontrado.");

        AgendamentoController.deletarAgendamento(agendamentoParaDeletar.getId().toString());

        Agendamento deletado = AgendamentoController.listarAgendamentos().stream()
                .filter(a -> a.getId().equals(agendamentoParaDeletar.getId()))
                .findFirst()
                .orElse(null);

        assertNull(deletado, "Agendamento não foi deletado.");
    }

    @Test
    void testListarAgendamentos() {
        List<Agendamento> lista = AgendamentoController.listarAgendamentos();
        assertNotNull(lista, "Lista retornou null.");
    }
}
