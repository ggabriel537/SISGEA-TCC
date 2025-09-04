package com.sisgea.BancoDados.Controllers;

import java.sql.Date;
import java.util.List;

import com.sisgea.BancoDados.Models.AgendamentoModel;
import com.sisgea.Entidades.Aeronave;
import com.sisgea.Entidades.Agendamento;
import com.sisgea.Entidades.Aluno;
import com.sisgea.Entidades.Instrutor;

public class AgendamentoController {
    public static void salvarAgendamento(Aeronave aeronave, Aluno aluno, Instrutor instrutor, String partida, String destino, String tipo_voo, String status, Date data_agendamento
    ) {
        Agendamento agendamento = new Agendamento();
        agendamento.setAeronave(aeronave);
        agendamento.setAluno(aluno);
        agendamento.setInstrutor(instrutor);
        agendamento.setPartida(partida);
        agendamento.setDestino(destino);
        agendamento.setTipo_voo(tipo_voo);
        agendamento.setStatus(status);
        agendamento.setData_agendamento(data_agendamento);
        AgendamentoModel.salvarAgendamento(agendamento);
    }

    public static void salvarAgendamento(Agendamento agendamento) {
        AgendamentoModel.salvarAgendamento(agendamento);
    }

    public static List<Agendamento> listarAgendamentos() {
        return AgendamentoModel.listarAgendamentos();
    } 

    public static Agendamento buscarId(String id) {
        return AgendamentoModel.buscarAgendamento(id);
    }

    public static void deletarAgendamento(String id) {
        AgendamentoModel.excluirAgendamento(id);
    }

    public static void atualizarAgendamento(Agendamento agendamento) {
        AgendamentoModel.atualizarAgendamento(agendamento);
    }
}