package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
        // Lista existente para validação
        List<DiarioBordo> diariosExistentes;
        try {
            diariosExistentes = DiarioBordoController.listarDiariosBordo();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao listar diários de bordo existentes: " + e.getMessage()));
        }

        // Inicializa variáveis
        String conflito_str = "";
        String warn_str = "";
        boolean conflito = false;
        boolean warn = false;

        //
        // DADOS OBRIGATÓRIOS
        //

        if (d.getAeronaveId() == null || d.getAeronaveId().isEmpty()) {
            conflito = true;
            conflito_str += "Aeronave é obrigatória.\n";
        }

        if (d.getNroDiario() == null) {
            conflito = true;
            conflito_str += "Número do diário é obrigatório.\n";
        }

        if (d.getData() == null) {
            conflito = true;
            conflito_str += "Data do diário de bordo é obrigatória.\n";
        }

        if (d.getDataDecolagem() == null || d.getDataPouso() == null) {
            conflito = true;
            conflito_str += "Datas de decolagem e pouso são obrigatórias.\n";
        }

        //
        // CONFLITOS
        //

        for (DiarioBordo existente : diariosExistentes) {
            // Mesmo número de diário para mesma aeronave
            if (existente.getAeronaveId().equals(d.getAeronaveId())
                    && existente.getNroDiario().equals(d.getNroDiario())) {
                conflito = true;
                conflito_str += "Já existe um diário com este número para esta aeronave.\n";
                break;
            }
        }

        // Data de pouso antes da decolagem
        if (d.getDataDecolagem() != null && d.getDataPouso() != null
                && d.getDataPouso().before(d.getDataDecolagem())) {
            conflito = true;
            conflito_str += "Data de pouso não pode ser antes da decolagem.\n";
        }

        //
        // WARNINGS
        //

        if (!conflito) {
            if (d.getHorasDiu() != null && d.getHorasNot() != null) {
                if (d.getHorasDiu() == 0 && d.getHorasNot() == 0) {
                    warn_str += "As horas de voo (diurno e noturno) estão zeradas.\n";
                    warn = true;
                }
            }
            if (d.getCombustivelUtilizado() != null && d.getCombustivelUtilizado() <= 0) {
                warn_str += "Combustível utilizado está zerado.\n";
                warn = true;
            }
        }

        //
        // BLOQUEIOS E AVISOS
        //

        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        if (warn && !forcar) {
            return ResponseEntity.ok(Map.of("warn", warn_str));
        }

        //
        // CADASTRO
        //

        DiarioBordoController.salvarDiarioBordo(d);
        return ResponseEntity.ok(Map.of("status", "sucesso", "diario", d.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody DiarioBordo d,
            @RequestParam(defaultValue = "false") boolean forcar) {

        // Busca existente
        DiarioBordo existente = DiarioBordoController.buscarId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Diário de Bordo não encontrado"));
        }

        List<DiarioBordo> diariosExistentes = DiarioBordoController.listarDiariosBordo();

        // Inicializa variáveis
        String conflito_str = "";
        String warn_str = "";
        boolean conflito = false;
        boolean warn = false;

        //
        // DADOS OBRIGATÓRIOS
        //

        if (d.getAeronaveId() == null || d.getAeronaveId().isEmpty()) {
            conflito = true;
            conflito_str += "Aeronave é obrigatória.\n";
        }

        if (d.getNroDiario() == null) {
            conflito = true;
            conflito_str += "Número do diário é obrigatório.\n";
        }

        if (d.getData() == null) {
            conflito = true;
            conflito_str += "Data do diário de bordo é obrigatória.\n";
        }

        if (d.getDataDecolagem() == null || d.getDataPouso() == null) {
            conflito = true;
            conflito_str += "Datas de decolagem e pouso são obrigatórias.\n";
        }

        //
        // CONFLITOS
        //

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

        //
        // WARNINGS
        //

        if (!conflito) {
            if (d.getHorasDiu() != null && d.getHorasNot() != null) {
                if (d.getHorasDiu() == 0 && d.getHorasNot() == 0) {
                    warn_str += "As horas de voo (diurno e noturno) estão zeradas.\n";
                    warn = true;
                }
            }
            if (d.getCombustivelUtilizado() != null && d.getCombustivelUtilizado() <= 0) {
                warn_str += "Combustível utilizado está zerado.\n";
                warn = true;
            }
        }

        //
        // BLOQUEIOS E AVISOS
        //

        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        if (warn && !forcar) {
            return ResponseEntity.ok(Map.of("warn", warn_str));
        }

        //
        // ATUALIZAÇÃO
        //

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
}
