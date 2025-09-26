package com.sisgea.sisgea.API.Controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.sisgea.BancoDados.Controllers.AeronaveController;
import com.sisgea.BancoDados.Controllers.AgendamentoController;
import com.sisgea.BancoDados.Controllers.AlunoController;
import com.sisgea.BancoDados.Controllers.InstrutorController;
import com.sisgea.Entidades.Aeronave;
import com.sisgea.Entidades.Agendamento;
import com.sisgea.Entidades.Aluno;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoAPI {

    @GetMapping("/{id}")
    public Agendamento buscar(@PathVariable String id) {
        Agendamento ag = AgendamentoController.buscarId(id);
        if (ag == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado");
        }
        return ag;
    }

    @GetMapping
    public List<Agendamento> listar() {
        return AgendamentoController.listarAgendamentos();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Agendamento ag, @RequestParam(defaultValue = "false") boolean forcar) {
        if (ag.getStatus() == null)
            ag.setStatus("Agendado");
        ag.setData_agendamento(new Date());

        String conflito_str = "";
        String warn_str = "";
        boolean conflito = false;
        boolean warn = false;

        //
        // DADOS OBRIGATÓRIOS
        //
        conflito_str += validarCamposObrigatorios(ag);
        if (!conflito_str.isEmpty()) {
            conflito = true;
        }

        //
        // CONFLITOS
        //
        

        if (ag.getHorario_partida().before(new Date())) {
            conflito = true;
            conflito_str += "O horário de partida não pode ser no passado.\n";
        }

        List<Agendamento> agendamentosExistentes = AgendamentoController.listarAgendamentos();
        for (Agendamento existente : agendamentosExistentes) {
            Date existenteInicio = existente.getHorario_partida();
            Date existenteFim = existente.getHorario_retorno() != null ? existente.getHorario_retorno()
                    : existenteInicio;
            Date novoInicio = ag.getHorario_partida();
            Date novoFim = ag.getHorario_retorno();

            if (existente.getAluno() != null && ag.getAluno() != null
                    && existente.getAluno().getCpf().equals(ag.getAluno().getCpf())) {
                if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                    conflito = true;
                    conflito_str += "Já existe um agendamento para este aluno dentro do período selecionado.\n";
                }
            }

            if (existente.getInstrutor() != null && ag.getInstrutor() != null
                    && existente.getInstrutor().getCpf().equals(ag.getInstrutor().getCpf())) {
                if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                    conflito = true;
                    conflito_str += "Já existe um agendamento para este instrutor dentro do período selecionado.\n";
                }
            }

            if (existente.getAeronave() != null && ag.getAeronave() != null
                    && existente.getAeronave().getMatricula().equals(ag.getAeronave().getMatricula())) {
                if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                    conflito = true;
                    conflito_str += "Existe agendamento da mesma aeronave dentro do período de partida e retorno.\n";
                }
            }

            if (conflito)
                break;
        }

        Aluno aluno = AlunoController.buscarId(ag.getAluno().getCpf());
        if (aluno.getHoras_compradas() <= 0) {
            conflito = true;
            conflito_str += "O aluno não possui horas de voo compradas.\n";
        }

        if (!conflito) {
            Aeronave aeronave = AeronaveController.buscarId(ag.getAeronave().getMatricula());
            int hora = ag.getHorario_partida().getHours();
            if ("VFR-D".equals(aeronave.getHabilitacao()) && (hora >= 18 || hora < 6)) {
                warn = true;
                warn_str += "Aeronave não homologada para o horário selecionado, confirme seus dados antes de prosseguir\n";
            }
        }

        if (conflito)
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str, "status", "CONFLITO"));
        if (warn && !forcar)
            return ResponseEntity.ok(Map.of("warn", warn_str));

        ag.setInstrutor(InstrutorController.buscarId(ag.getInstrutor().getCpf()));
        AgendamentoController.salvarAgendamento(ag);

        return ResponseEntity.ok(Map.of("status", "sucesso", "agendamento", ag.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody Agendamento ag,
            @RequestParam(defaultValue = "false") boolean forcar) {
        Agendamento existenteAtual = AgendamentoController.buscarId(id);
        if (existenteAtual == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Agendamento não encontrado"));
        }

        if (ag.getStatus() == null)
            ag.setStatus(existenteAtual.getStatus() != null ? existenteAtual.getStatus() : "Agendado");

        String erroObrigatorio = validarCamposObrigatorios(ag);
        if (!erroObrigatorio.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erroObrigatorio, "status", "CONFLITO"));
        }

        String conflito_str = "";
        String warn_str = "";
        boolean conflito = false;
        boolean warn = false;

        if (ag.getHorario_partida().before(new Date())) {
            conflito = true;
            conflito_str += "O horário de partida não pode ser no passado.\n";
        }

        List<Agendamento> agendamentosExistentes = AgendamentoController.listarAgendamentos();
        for (Agendamento outro : agendamentosExistentes) {
            if (outro.getId().equals(ag.getId()))
                continue;

            Date existenteInicio = outro.getHorario_partida();
            Date existenteFim = outro.getHorario_retorno() != null ? outro.getHorario_retorno() : existenteInicio;
            Date novoInicio = ag.getHorario_partida();
            Date novoFim = ag.getHorario_retorno();

            if (outro.getAluno() != null && ag.getAluno() != null
                    && outro.getAluno().getCpf().equals(ag.getAluno().getCpf())) {
                if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                    conflito = true;
                    conflito_str += "Já existe um agendamento para este aluno dentro do período selecionado.\n";
                }
            }

            if (outro.getInstrutor() != null && ag.getInstrutor() != null
                    && outro.getInstrutor().getCpf().equals(ag.getInstrutor().getCpf())) {
                if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                    conflito = true;
                    conflito_str += "Já existe um agendamento para este instrutor dentro do período selecionado.\n";
                }
            }

            if (outro.getAeronave() != null && ag.getAeronave() != null
                    && outro.getAeronave().getMatricula().equals(ag.getAeronave().getMatricula())) {
                if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                    conflito = true;
                    conflito_str += "Existe agendamento da mesma aeronave dentro do período de partida e retorno.\n";
                }
            }

            if (conflito)
                break;
        }

        Aluno aluno = AlunoController.buscarId(ag.getAluno().getCpf());
        if (aluno.getHoras_compradas() <= 0) {
            conflito = true;
            conflito_str += "O aluno não possui horas de voo compradas.\n";
        }

        if (!conflito) {
            Aeronave aeronave = AeronaveController.buscarId(ag.getAeronave().getMatricula());
            int hora = ag.getHorario_partida().getHours();
            if ("VFR-D".equals(aeronave.getHabilitacao()) && (hora >= 18 || hora < 6)) {
                warn = true;
                warn_str += "Aeronave não homologada para o horário selecionado, confirme seus dados antes de prosseguir\n";
            }
        }

        if (conflito)
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str, "status", "CONFLITO"));
        if (warn && !forcar)
            return ResponseEntity.ok(Map.of("warn", warn_str));

        ag.setId(UUID.fromString(id));
        AgendamentoController.atualizarAgendamento(ag);

        return ResponseEntity.ok(Map.of("status", "sucesso", "agendamento", ag.getId()));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        Agendamento ag = AgendamentoController.buscarId(id);
        if (ag == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado");
        }
        AgendamentoController.deletarAgendamento(ag.getId().toString());
    }

    private String validarCamposObrigatorios(Agendamento ag) {
        String msg = "";
        if (ag.getHorario_partida() == null || ag.getHorario_retorno() == null)
            msg += "Campos 'Horario Partida' e 'Horario Retorno' são obrigatórios. ";
        if (ag.getHorario_retorno() != null && ag.getHorario_partida() != null
                && ag.getHorario_retorno().before(ag.getHorario_partida()))
            msg += "'Horario Retorno' deve ser posterior ou igual ao 'Horario Partida'. ";
        if (ag.getAluno() == null || ag.getInstrutor() == null || ag.getAeronave() == null || ag.getAluno().getCpf() == null
                || ag.getAluno().getCpf().isBlank() || ag.getInstrutor().getCpf() == null
                || ag.getInstrutor().getCpf().isBlank() || ag.getAeronave().getMatricula() == null
                || ag.getAeronave().getMatricula().isBlank())
            msg += "Aluno, instrutor e aeronave são obrigatórios. ";
        if (ag.getPartida() == null || ag.getPartida().isBlank() || ag.getDestino() == null
                || ag.getDestino().isBlank())
            msg += "Campos 'Origem' e 'Destino' são obrigatórios. ";
        if (ag.getTipo_voo() == null || ag.getTipo_voo().isBlank())
            msg += "Tipo de voo é obrigatório. ";
        return msg.trim();
    }
}
