package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sisgea.BancoDados.Controllers.DiscrepanciaController;
import com.sisgea.Entidades.Discrepancia;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/discrepancias")
public class DiscrepanciaAPI {

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id) {
        Discrepancia d = DiscrepanciaController.buscarId(id);
        if (d == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Discrepância não encontrada"));
        }
        return ResponseEntity.ok(d);
    }

    @GetMapping
    public ResponseEntity<List<Discrepancia>> listar() {
        return ResponseEntity.ok(DiscrepanciaController.listarDiscrepancias());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Discrepancia d, @RequestParam(defaultValue = "false") boolean forcar) {

        // Conflito -> Bloqueia o cadastro
        // Warn -> Apenas avisa o usuário, mas permite o cadastro
        String conflito_str = "";
        String warn_str = "";
        boolean warn = false;
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //
        if (d.getData() == null) {
            conflito = true;
            conflito_str += "Data é obrigatória.\n";
        }
        if (d.getSistema() == null || d.getSistema().isBlank()) {
            conflito = true;
            conflito_str += "Sistema é obrigatório.\n";
        }
        if (d.getDiscrepancia() == null || d.getDiscrepancia().isBlank()) {
            conflito = true;
            conflito_str += "Discrepância é obrigatória.\n";
        }
        if (d.getCorrecao() == null || d.getCorrecao().isBlank()) {
            conflito = true;
            conflito_str += "Correção é obrigatória.\n";
        }

        //
        // WARNINGS
        //
        if (!conflito) {
            if (d.getCorrecao() != null && d.getCorrecao().length() < 10) {
                warn = true;
                warn_str += "Descrição da correção muito curta, confirme antes de prosseguir.\n";
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
        // CADASTRO DA DISCREPÂNCIA
        //
        DiscrepanciaController.salvarDiscrepancia(d);
        return ResponseEntity.ok(Map.of("status", "sucesso", "id", d.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody Discrepancia d,
                                       @RequestParam(defaultValue = "false") boolean forcar) {
        // Busca discrepância existente
        Discrepancia existente = DiscrepanciaController.buscarId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Discrepância não encontrada"));
        }

        // Inicializa variáveis de conflito e warning
        String conflito_str = "";
        String warn_str = "";
        boolean warn = false;
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //
        if (d.getData() == null) {
            conflito = true;
            conflito_str += "Data é obrigatória.\n";
        }
        if (d.getSistema() == null || d.getSistema().isBlank()) {
            conflito = true;
            conflito_str += "Sistema é obrigatório.\n";
        }
        if (d.getDiscrepancia() == null || d.getDiscrepancia().isBlank()) {
            conflito = true;
            conflito_str += "Discrepância é obrigatória.\n";
        }
        if (d.getCorrecao() == null || d.getCorrecao().isBlank()) {
            conflito = true;
            conflito_str += "Correção é obrigatória.\n";
        }

        //
        // WARNINGS
        //
        if (!conflito) {
            if (d.getCorrecao() != null && d.getCorrecao().length() < 10) {
                warn = true;
                warn_str += "Descrição da correção muito curta, confirme antes de prosseguir.\n";
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
        // ATUALIZAÇÃO DA DISCREPÂNCIA
        //
        d.setId(UUID.fromString(id));
        DiscrepanciaController.atualizarDiscrepancia(d);
        return ResponseEntity.ok(Map.of("status", "sucesso", "id", d.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id) {
        Discrepancia d = DiscrepanciaController.buscarId(id);
        if (d == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Discrepância não encontrada"));
        }
        DiscrepanciaController.deletarDiscrepancia(id);
        return ResponseEntity.ok(Map.of("status", "deletado", "id", id));
    }
}
