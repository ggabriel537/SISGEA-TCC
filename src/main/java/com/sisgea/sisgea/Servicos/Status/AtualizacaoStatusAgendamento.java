package com.sisgea.sisgea.Servicos.Status;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sisgea.BancoDados.Controllers.AgendamentoController;
import com.sisgea.Entidades.Agendamento;

import jakarta.transaction.Transactional;

@Service
public class AtualizacaoStatusAgendamento {
   List<Agendamento> agendamentos = null;

    public AtualizacaoStatusAgendamento() {
    }

    // Executa a atualização a cada 15 minutos
    @Transactional
    @Scheduled(fixedRate = 60000*15)
    public void atualizarStatusAgendamentos() {
        agendamentos = AgendamentoController.listarAgendamentos();
        if (agendamentos == null) {
            System.out.println("Nenhum agendamento encontrado para atualizar.");
            return;
        }
        LocalDateTime agora = LocalDateTime.now();
        for (Agendamento agendamento : agendamentos) {
            LocalDateTime dataAgendamento = agendamento.getHorario_partida().toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime();
            if (dataAgendamento.isBefore(agora) && agendamento.getStatus().equals("Agendado")) {
                System.out.println("Atualizando status do agendamento ID: " + agendamento.getId());
                agendamento.setStatus("Concluído");
                AgendamentoController.atualizarAgendamento(agendamento);
            }
        }
        System.out.println("Serviço de atualização de status de agendamentos executado em: " + agora);
    }
}
