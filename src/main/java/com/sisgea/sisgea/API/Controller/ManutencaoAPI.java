package com.sisgea.sisgea.API.Controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sisgea.BancoDados.Controllers.ManutencaoController;
import com.sisgea.Entidades.Manutencao;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/manutencoes")
public class ManutencaoAPI {

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id) {
        Manutencao m = ManutencaoController.buscarId(id);
        if (m == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Manutenção não encontrada"));
        }
        return ResponseEntity.ok(m);
    }

    @GetMapping
    public ResponseEntity<List<Manutencao>> listar() {
        return ResponseEntity.ok(ManutencaoController.listarManutencoes());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Manutencao m, @RequestParam(defaultValue = "false") boolean forcar) {
        // Limitações de Cadastro das manutenções
        List<Manutencao> manutencoesExistentes = null;
        try {
            manutencoesExistentes = ManutencaoController.listarManutencoes();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao listar manutenções existentes para validação: " + e.getMessage()));
        }

        // Conflito -> Bloqueia o cadastro
        // Warn -> Apenas avisa o usuário, mas permite o cadastro
        String conflito_str = "";
        String warn_str = "";
        boolean warn = false;
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //
        if (m.getDescricao() == null || m.getDescricao().isBlank()) {
            conflito = true;
            conflito_str += "Descrição é obrigatória.\n";
        }
        if (m.getData_est_man() == null) {
            conflito = true;
            conflito_str += "Data estimada é obrigatória.\n";
        }
        if (m.getStatus() == null || m.getStatus().isBlank()) {
            conflito = true;
            conflito_str += "Status é obrigatório.\n";
        }

        //
        // CONFLITOS
        //
        for (Manutencao existente : manutencoesExistentes) {
            // Conflito: mesma descrição e mesma data estimada (duplicidade típica)
            if (existente.getDescricao().equals(m.getDescricao())
                && existente.getData_est_man() != null
                && existente.getData_est_man().equals(m.getData_est_man())) {
                conflito = true;
                conflito_str += "Já existe uma manutenção com a mesma descrição e data estimada.\n";
                break;
            }
        }

        //
        // WARNINGS
        //
        if (!conflito) {
            // Data estimada no passado
            if (m.getData_est_man() != null && m.getData_est_man().before(new Date())) {
                warn = true;
                warn_str += "Data estimada está no passado, confirme antes de prosseguir.\n";
            }
        }

        // Se houver conflito, bloqueia o cadastro
        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        // Se houver apenas warn, avisa o usuário, mas permite o cadastro
        if (warn && !forcar) {
            return ResponseEntity.ok(Map.of("warn", warn_str));
        }

        //
        // CADASTRO DA MANUTENÇÃO
        //
        ManutencaoController.salvarManutencao(m);
        return ResponseEntity.ok(Map.of("status", "sucesso", "id", m.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody Manutencao m,
                                       @RequestParam(defaultValue = "false") boolean forcar) {
        // Busca manutenção existente
        Manutencao existente = ManutencaoController.buscarId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Manutenção não encontrada"));
        }

        // Lista todas as manutenções para validação
        List<Manutencao> manutencoesExistentes = null;
        try {
            manutencoesExistentes = ManutencaoController.listarManutencoes();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao listar manutenções existentes para validação: " + e.getMessage()));
        }

        // Inicializa variáveis de conflito e warning
        String conflito_str = "";
        String warn_str = "";
        boolean warn = false;
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //
        if (m.getDescricao() == null || m.getDescricao().isBlank()) {
            conflito = true;
            conflito_str += "Descrição é obrigatória.\n";
        }
        if (m.getData_est_man() == null) {
            conflito = true;
            conflito_str += "Data estimada é obrigatória.\n";
        }
        if (m.getStatus() == null || m.getStatus().isBlank()) {
            conflito = true;
            conflito_str += "Status é obrigatório.\n";
        }

        //
        // CONFLITOS
        //
        for (Manutencao outro : manutencoesExistentes) {
            if (outro.getId() == null || outro.getId().equals(m.getId())) continue;
            if (outro.getDescricao().equals(m.getDescricao())
                && outro.getData_est_man() != null
                && outro.getData_est_man().equals(m.getData_est_man())) {
                conflito = true;
                conflito_str += "Já existe uma manutenção com a mesma descrição e data estimada.\n";
                break;
            }
        }

        //
        // WARNINGS
        //
        if (!conflito) {
            if (m.getData_est_man() != null && m.getData_est_man().before(new Date())) {
                warn = true;
                warn_str += "Data estimada está no passado, confirme antes de prosseguir.\n";
            }

        }

        //
        // BLOQUEIA ATUALIZAÇÃO SE HOUVER CONFLITO
        //
        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        //
        // AVISA USUÁRIO SE HOUVER WARNING
        //
        if (warn && !forcar) {
            return ResponseEntity.ok(Map.of("warn", warn_str));
        }

        //
        // ATUALIZAÇÃO DA MANUTENÇÃO
        //
        m.setId(UUID.fromString(id));
        ManutencaoController.atualizarManutencao(m);
        return ResponseEntity.ok(Map.of("status", "sucesso", "id", m.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id) {
        Manutencao m = ManutencaoController.buscarId(id);
        if (m == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Manutenção não encontrada"));
        }
        ManutencaoController.deletarManutencao(id);
        return ResponseEntity.ok(Map.of("status", "deletado", "id", id));
    }
}
