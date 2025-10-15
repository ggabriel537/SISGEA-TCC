package com.sisgea.sisgea.Servicos.Status;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sisgea.BancoDados.Controllers.ManutencaoController;
import com.sisgea.Entidades.Manutencao;

import jakarta.transaction.Transactional;

@Service
public class AtualizacaoStatusManutencao {
   List<Manutencao> manutencoes = null;

    public AtualizacaoStatusManutencao() {
    }

    // Executa a atualização a cada 15 minutos
    @Transactional
    @Scheduled(fixedRate = 60000*15)
    public void atualizarStatusManutencoes() {
        manutencoes = ManutencaoController.listarManutencoes();
        if (manutencoes == null) {
            System.out.println("Nenhuma manutenção encontrada para atualizar.");
            return;
        }
        LocalDateTime agora = LocalDateTime.now();
        for (Manutencao manutencao : manutencoes) {
            LocalDateTime dataManutencao = manutencao.getData_est_man().toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime();
            if (dataManutencao.isBefore(agora) && manutencao.getStatus().equals("Pendente")) {
                System.out.println("Atualizando status da manutenção ID: " + manutencao.getId());
                manutencao.setStatus("Em andamento");
                ManutencaoController.atualizarManutencao(manutencao);
            }
        }
        System.out.println("Serviço de atualização de status de manutenções executado em: " + agora);
    }
}
