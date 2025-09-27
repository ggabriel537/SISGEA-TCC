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
        // Busca diário de bordo pelo ID
        DiarioBordo d = DiarioBordoController.buscarId(id);
        if (d == null) {
            // Retorna 404 se não encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diário de Bordo não encontrado");
        }
        return d;
    }

    @GetMapping
    public List<DiarioBordo> listar() {
        // Lista todos os diários de bordo
        return DiarioBordoController.listarDiariosBordo();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody DiarioBordo d, @RequestParam(defaultValue = "false") boolean forcar) {

        //
        // VALIDAÇÃO DE CAMPOS OBRIGATÓRIOS
        //
        String erroObrigatorio = validarCamposObrigatorios(d, true);
        if (!erroObrigatorio.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erroObrigatorio, "status", "CONFLITO"));
        }

        //
        // VALIDAÇÃO DE CONFLITOS
        //
        String conflito_str = validarConflitos(d, null);
        if (!conflito_str.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error", conflito_str, "status", "CONFLITO"));

        //
        // CADASTRO DO DIÁRIO DE BORDO
        //
        DiarioBordoController.salvarDiarioBordo(d);
        return ResponseEntity.ok(Map.of("status", "sucesso", "diario", d.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody DiarioBordo d,
                                       @RequestParam(defaultValue = "false") boolean forcar) {

        // Busca diário existente
        DiarioBordo existente = DiarioBordoController.buscarId(id);
        if (existente == null) {
            // Retorna 404 se não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Diário de Bordo não encontrado"));
        }

        //
        // VALIDAÇÃO DE CAMPOS OBRIGATÓRIOS
        //
        String erroObrigatorio = validarCamposObrigatorios(d, false);
        if (!erroObrigatorio.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erroObrigatorio, "status", "CONFLITO"));
        }

        //
        // VALIDAÇÃO DE CONFLITOS
        //
        String conflito_str = validarConflitos(d, id);
        if (!conflito_str.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error", conflito_str, "status", "CONFLITO"));

        //
        // ATUALIZAÇÃO DO DIÁRIO DE BORDO
        //
        d.setId(UUID.fromString(id));
        DiarioBordoController.atualizarDiarioBordo(d);
        return ResponseEntity.ok(Map.of("status", "sucesso", "diario", d.getId()));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        // Busca diário de bordo pelo ID
        DiarioBordo d = DiarioBordoController.buscarId(id);
        if (d == null) {
            // Retorna 404 se não encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diário de Bordo não encontrado");
        }
        // Deleta diário de bordo
        DiarioBordoController.deletarDiarioBordo(id);
    }

    //
    // MÉTODO AUXILIAR: VALIDA CAMPOS OBRIGATÓRIOS
    //
    private String validarCamposObrigatorios(DiarioBordo d, boolean isCreate) {
        String msg = "";
        if (d.getAeronaveId() == null || d.getAeronaveId().isBlank())
            msg += "Aeronave é obrigatória. ";
        if (d.getNroDiario() == null)
            msg += "Número do diário é obrigatório. ";
        if (d.getData() == null)
            msg += "Data do diário de bordo é obrigatória. ";
        if (d.getAlunoId() == null || d.getAlunoId().isBlank())
            msg += "Aluno é obrigatório. ";
        if (d.getInstrutorId() == null || d.getInstrutorId().isBlank())
            msg += "Instrutor é obrigatório. ";
        if (d.getFuncaoAluno() == null || d.getFuncaoAluno().isBlank())
            msg += "Função do aluno é obrigatória. ";
        if (d.getFuncaoInstrutor() == null || d.getFuncaoInstrutor().isBlank())
            msg += "Função do instrutor é obrigatória. ";
        if (d.getHoraAeronave() == null)
            msg += "Hora da aeronave é obrigatória. ";
        if (d.getDataDecolagem() == null)
            msg += "Data de decolagem é obrigatória. ";
        if (d.getDataPouso() == null)
            msg += "Data de pouso é obrigatória. ";
        if (d.getDataCorte() == null)
            msg += "Data de corte é obrigatória. ";
        if (d.getCombustivelUtilizado() == null)
            msg += "Combustível utilizado é obrigatório. ";
        if (d.getCiclos() == null)
            msg += "Ciclos são obrigatórios. ";
        if (d.getPob() == null)
            msg += "POB é obrigatório. ";
        if (d.getCarga() == null)
            msg += "Carga é obrigatória. ";
        if (d.getNat() == null || d.getNat().isBlank())
            msg += "Natureza é obrigatória. ";

        boolean todasHorasVazias = (d.getHorasDiu() == null && d.getHorasNot() == null &&
                d.getHorasVfr() == null && d.getHorasIfr() == null && d.getHorasIfrC() == null);
        if (todasHorasVazias)
            msg += "Pelo menos uma hora deve ser preenchida. ";

        if (d.getDataCorte() != null && d.getDataPouso() != null && d.getDataCorte().before(d.getDataPouso()))
            msg += "Data de corte não pode ser anterior à data de pouso. ";

        return msg.trim();
    }

    //
    // MÉTODO AUXILIAR: VALIDA CONFLITOS ENTRE DIÁRIOS
    //
    private String validarConflitos(DiarioBordo d, String idAtual) {
        String conflito_str = "";
        List<DiarioBordo> diariosExistentes = DiarioBordoController.listarDiariosBordo();

        // Verifica se já existe diário com o mesmo número para a mesma aeronave
        for (DiarioBordo outro : diariosExistentes) {
            if (idAtual != null && outro.getId().toString().equals(idAtual)) continue;
            if (outro.getAeronaveId().equals(d.getAeronaveId())
                    && outro.getNroDiario().equals(d.getNroDiario())) {
                conflito_str += "Já existe um diário com este número para esta aeronave.\n";
                break;
            }
        }

        // Verifica se data de pouso é anterior à decolagem
        if (d.getDataDecolagem() != null && d.getDataPouso() != null
                && d.getDataPouso().before(d.getDataDecolagem())) {
            conflito_str += "Data de pouso não pode ser antes da decolagem.\n";
        }

        return conflito_str;
    }
}
