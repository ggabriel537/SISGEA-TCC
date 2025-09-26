package com.sisgea.sisgea.API.Controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sisgea.BancoDados.Controllers.AeronaveController;
import com.sisgea.BancoDados.Controllers.ManutencaoController;
import com.sisgea.Entidades.Aeronave;
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
                    .body("ERRO: Manutenção não encontrada.");
        }
        return ResponseEntity.ok(m);
    }

    @GetMapping
    public ResponseEntity<List<Manutencao>> listar() {
        return ResponseEntity.ok(ManutencaoController.listarManutencoes());
    }

    @PostMapping
public ResponseEntity<?> criar(@RequestBody Manutencao m, @RequestParam(defaultValue = "false") boolean forcar) {
    StringBuilder conflitoStr = new StringBuilder();
    StringBuilder warnStr = new StringBuilder();
    boolean conflito = false;
    boolean warn = false;

    if (m.getDescricao() == null || m.getDescricao().isBlank()) {
        conflito = true;
        conflitoStr.append("Descrição é obrigatória.\n");
    }
    if (m.getData_est_man() == null) {
        conflito = true;
        conflitoStr.append("Data estimada é obrigatória.\n");
    }
    if (m.getStatus() == null || m.getStatus().isBlank()) {
        conflito = true;
        conflitoStr.append("Status é obrigatório.\n");
    }

    if (!conflito && m.getData_est_man() != null && m.getData_est_man().before(new Date())) {
        warn = true;
        warnStr.append("A data estimada está no passado, confirme antes de prosseguir.\n");
    }

    if (conflito) {
        return ResponseEntity.badRequest().body("ERRO:\n" + conflitoStr);
    }

    if (warn && !forcar) {
        return ResponseEntity.ok("AVISO:\n" + warnStr);
    }

    if (m.getAeronave() == null || m.getAeronave().getMatricula() == null) {
        return ResponseEntity.badRequest().body("ERRO: Aeronave é obrigatória.");
    }

    // Supondo que exista um AeronaveController com método buscarPorMatricula
    Aeronave aeronave = AeronaveController.buscarId(m.getAeronave().getMatricula());
    if (aeronave == null) {
        return ResponseEntity.badRequest().body("ERRO: Aeronave não encontrada.");
    }

    m.setAeronave(aeronave);

    ManutencaoController.salvarManutencao(m);
    return ResponseEntity.ok("SUCESSO: Manutenção cadastrada com ID " + m.getId());
}


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody Manutencao m,
                                       @RequestParam(defaultValue = "false") boolean forcar) {
        Manutencao existente = ManutencaoController.buscarId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ERRO: Manutenção não encontrada.");
        }

        StringBuilder conflitoStr = new StringBuilder();
        StringBuilder warnStr = new StringBuilder();
        boolean conflito = false;
        boolean warn = false;

        if (m.getDescricao() == null || m.getDescricao().isBlank()) {
            conflito = true;
            conflitoStr.append("Descrição é obrigatória.\n");
        }
        if (m.getData_est_man() == null) {
            conflito = true;
            conflitoStr.append("Data estimada é obrigatória.\n");
        }
        if (m.getStatus() == null || m.getStatus().isBlank()) {
            conflito = true;
            conflitoStr.append("Status é obrigatório.\n");
        }

        if (!conflito && m.getData_est_man() != null && m.getData_est_man().before(new Date())) {
            warn = true;
            warnStr.append("A data estimada está no passado, confirme antes de prosseguir.\n");
        }

        if (conflito) {
            return ResponseEntity.badRequest().body("ERRO:\n" + conflitoStr);
        }

        if (warn && !forcar) {
            return ResponseEntity.ok("AVISO:\n" + warnStr);
        }

        m.setId(UUID.fromString(id));
        ManutencaoController.atualizarManutencao(m);
        return ResponseEntity.ok("SUCESSO: Manutenção atualizada com ID " + m.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id) {
        Manutencao m = ManutencaoController.buscarId(id);
        if (m == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ERRO: Manutenção não encontrada.");
        }
        ManutencaoController.deletarManutencao(id);
        return ResponseEntity.ok("SUCESSO: Manutenção deletada com ID " + id);
    }
}
