package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.sisgea.BancoDados.Controllers.DiarioBordoController;
import com.sisgea.Entidades.DiarioBordo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/diarios-bordo")
public class DiarioBordoAPI {

    @GetMapping("/{id}")
    public DiarioBordo buscarId(@PathVariable String id) {
        DiarioBordo d = DiarioBordoController.buscarId(id);
        if (d == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diário de Bordo não encontrado");
        }
        return d;
    }

    @GetMapping
    public List<DiarioBordo> listar() {
        return DiarioBordoController.listarDiariosBordo();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody DiarioBordo d, @RequestParam(defaultValue = "false") boolean forcar) {
        String erroObrigatorio = validarCamposObrigatorios(d);
        if (!erroObrigatorio.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erroObrigatorio, "status", "CONFLITO"));
        }

        String conflito_str = "";
        String warn_str = "";
        boolean conflito = false;
        boolean warn = false;

        List<DiarioBordo> diariosExistentes = DiarioBordoController.listarDiariosBordo();

        for (DiarioBordo existente : diariosExistentes) {
            if (existente.getAeronaveId().equals(d.getAeronaveId())
                    && existente.getNroDiario().equals(d.getNroDiario())) {
                conflito = true;
                conflito_str += "Já existe um diário com este número para esta aeronave.\n";
                break;
            }
        }

        if (d.getDataDecolagem() != null && d.getDataPouso() != null
                && d.getDataPouso().before(d.getDataDecolagem())) {
            conflito = true;
            conflito_str += "Data de pouso não pode ser antes da decolagem.\n";
        }

        if (!conflito) {
            if (d.getCombustivelUtilizado() != null && d.getCombustivelUtilizado() <= 0) {
                warn = true;
                warn_str += "Combustível utilizado está zerado.\n";
            }
        }

        if (conflito) return ResponseEntity.badRequest().body(Map.of("error", conflito_str, "status", "CONFLITO"));
        if (warn && !forcar) return ResponseEntity.ok(Map.of("warn", warn_str));

        DiarioBordoController.salvarDiarioBordo(d);
        return ResponseEntity.ok(Map.of("status", "sucesso", "diario", d.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody DiarioBordo d,
                                       @RequestParam(defaultValue = "false") boolean forcar) {

        DiarioBordo existente = DiarioBordoController.buscarId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Diário de Bordo não encontrado"));
        }

        String erroObrigatorio = validarCamposObrigatorios(d);
        if (!erroObrigatorio.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erroObrigatorio, "status", "CONFLITO"));
        }

        String conflito_str = "";
        String warn_str = "";
        boolean conflito = false;
        boolean warn = false;

        List<DiarioBordo> diariosExistentes = DiarioBordoController.listarDiariosBordo();

        for (DiarioBordo outro : diariosExistentes) {
            if (outro.getId().equals(d.getId())) continue;
            if (outro.getAeronaveId().equals(d.getAeronaveId())
                    && outro.getNroDiario().equals(d.getNroDiario())) {
                conflito = true;
                conflito_str += "Já existe um diário com este número para esta aeronave.\n";
                break;
            }
        }

        if (d.getDataDecolagem() != null && d.getDataPouso() != null
                && d.getDataPouso().before(d.getDataDecolagem())) {
            conflito = true;
            conflito_str += "Data de pouso não pode ser antes da decolagem.\n";
        }

        if (!conflito) {
            if (d.getCombustivelUtilizado() != null && d.getCombustivelUtilizado() <= 0) {
                warn = true;
                warn_str += "Combustível utilizado está zerado.\n";
            }
        }

        if (conflito) return ResponseEntity.badRequest().body(Map.of("error", conflito_str, "status", "CONFLITO"));
        if (warn && !forcar) return ResponseEntity.ok(Map.of("warn", warn_str));

        d.setId(UUID.fromString(id));
        DiarioBordoController.atualizarDiarioBordo(d);
        return ResponseEntity.ok(Map.of("status", "sucesso", "diario", d.getId()));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        DiarioBordo d = DiarioBordoController.buscarId(id);
        if (d == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diário de Bordo não encontrado");
        }
        DiarioBordoController.deletarDiarioBordo(id);
    }

    private String validarCamposObrigatorios(DiarioBordo d) {
        String msg = "";
        if (d.getAeronaveId() == null || d.getAeronaveId().isBlank())
            msg += "Aeronave é obrigatória. ";
        if (d.getNroDiario() == null)
            msg += "Número do diário é obrigatório. ";
        if (d.getData() == null)
            msg += "Data do diário de bordo é obrigatória. ";
        if (d.getDataDecolagem() == null || d.getDataPouso() == null)
            msg += "Datas de decolagem e pouso são obrigatórias. ";
        if (d.getLocalDecolagem() == null || d.getLocalDecolagem().isBlank()
                || d.getLocalPouso() == null || d.getLocalPouso().isBlank())
            msg += "Local de decolagem e local de pouso são obrigatórios. ";
        return msg.trim();
    }
}
