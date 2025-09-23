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
        //
        // BUSCA AGENDAMENTO EXISTENTE
        //
        Agendamento ag = AgendamentoController.buscarId(id);
        if (ag == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado");
        }
        return ag;
    }

    @GetMapping
    public List<Agendamento> listar() {
        //
        // LISTA TODOS OS AGENDAMENTOS
        //
        return AgendamentoController.listarAgendamentos();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Agendamento ag, @RequestParam(defaultValue = "false") boolean forcar) {

        //
        // DEFINIÇÃO DE STATUS PADRÃO E DATA DO AGENDAMENTO
        //
        if (ag.getStatus() == null) ag.setStatus("Agendado");
        ag.setData_agendamento(new Date());

        //
        // VALIDAÇÃO DE CAMPOS OBRIGATÓRIOS
        //
        if (ag.getHorario_partida() == null || ag.getHorario_retorno() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Campos 'horario_partida' e 'horario_retorno' são obrigatórios."));
        }
        if (ag.getHorario_retorno().before(ag.getHorario_partida())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "'horario_retorno' deve ser posterior ou igual ao 'horario_partida'."));
        }

        //
        // VERIFICAÇÃO DE CONFLITOS E REGRAS
        //
        String conflito_str = "";
        String warn_str = "";
        boolean conflito = false;
        boolean warn = false;

        if (ag.getAluno() == null || ag.getInstrutor() == null || ag.getAeronave() == null) {
            conflito_str += "Aluno, instrutor e aeronave são obrigatórios.\n";
            conflito = true;
        }
        if (ag.getHorario_partida().before(new Date())) {
            conflito_str += "O horário de partida não pode ser no passado.\n";
            conflito = true;
        }

        List<Agendamento> agendamentosExistentes = AgendamentoController.listarAgendamentos();

        for (Agendamento existente : agendamentosExistentes) {
            // Conflito de horário - aluno
            if (existente.getAluno() != null && ag.getAluno() != null
                    && existente.getAluno().getCpf().equals(ag.getAluno().getCpf())) {
                Date existenteInicio = existente.getHorario_partida();
                Date existenteFim = existente.getHorario_retorno() != null ? existente.getHorario_retorno() : existenteInicio;
                Date novoInicio = ag.getHorario_partida();
                Date novoFim = ag.getHorario_retorno();

                if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                    conflito = true;
                    conflito_str += "Já existe um agendamento para este aluno dentro do período selecionado.\n";
                }
            }

            // Conflito de horário - instrutor
            if (existente.getInstrutor() != null && ag.getInstrutor() != null
                    && existente.getInstrutor().getCpf().equals(ag.getInstrutor().getCpf())) {
                Date existenteInicio = existente.getHorario_partida();
                Date existenteFim = existente.getHorario_retorno() != null ? existente.getHorario_retorno() : existenteInicio;
                Date novoInicio = ag.getHorario_partida();
                Date novoFim = ag.getHorario_retorno();

                if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                    conflito = true;
                    conflito_str += "Já existe um agendamento para este instrutor dentro do período selecionado.\n";
                }
            }

            // Conflito de horário - aeronave
            if (existente.getAeronave() != null && ag.getAeronave() != null
                    && existente.getAeronave().getMatricula().equals(ag.getAeronave().getMatricula())) {
                Date existenteInicio = existente.getHorario_partida();
                Date existenteFim = existente.getHorario_retorno() != null ? existente.getHorario_retorno() : existenteInicio;
                Date novoInicio = ag.getHorario_partida();
                Date novoFim = ag.getHorario_retorno();

                if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                    conflito = true;
                    conflito_str += "Existe agendamento da mesma aeronave dentro do período de partida e retorno.\n";
                }
            }

            if (conflito) break;
        }

        // Verifica horas de voo do aluno
        Aluno aluno = AlunoController.buscarId(ag.getAluno().getCpf());
        if (aluno.getHoras_compradas() <= 0) {
            conflito = true;
            conflito_str += "O aluno não possui horas de voo compradas.\n";
        }

        // Aviso de aeronave não homologada
        if (!conflito) {
            Aeronave aeronave = AeronaveController.buscarId(ag.getAeronave().getMatricula());
            int hora = ag.getHorario_partida().getHours();
            if ("VFR-D".equals(aeronave.getHabilitacao()) && (hora >= 18 || hora < 6)) {
                warn = true;
                warn_str += "Aeronave não homologada para o horário selecionado, confirme seus dados antes de prosseguir\n";
            }
        }

        //
        // RETORNA CONFLITOS OU AVISOS
        //
        if (conflito) return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        if (warn && !forcar) return ResponseEntity.ok(Map.of("warn", warn_str));

        //
        // CADASTRO DO AGENDAMENTO
        //
        ag.setInstrutor(InstrutorController.buscarId(ag.getInstrutor().getCpf()));
        AgendamentoController.salvarAgendamento(ag);

        return ResponseEntity.ok(Map.of("status", "sucesso", "agendamento", ag.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody Agendamento ag,
            @RequestParam(defaultValue = "false") boolean forcar) {

        //
        // BUSCA AGENDAMENTO EXISTENTE
        //
        Agendamento existenteAtual = AgendamentoController.buscarId(id);
        if (existenteAtual == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Agendamento não encontrado"));
        }

        //
        // MANTÉM STATUS ATUAL SE NÃO INFORMADO
        //
        if (ag.getStatus() == null) ag.setStatus(existenteAtual.getStatus() != null ? existenteAtual.getStatus() : "Agendado");

        //
        // VALIDAÇÃO DE CAMPOS OBRIGATÓRIOS
        //
        if (ag.getHorario_partida() == null || ag.getHorario_retorno() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Campos 'horario_partida' e 'horario_retorno' são obrigatórios."));
        }
        if (ag.getHorario_retorno().before(ag.getHorario_partida())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "'horario_retorno' deve ser posterior ou igual ao 'horario_partida'."));
        }

        //
        // VERIFICAÇÃO DE CONFLITOS E REGRAS
        //
        String conflito_str = "";
        String warn_str = "";
        boolean conflito = false;
        boolean warn = false;

        if (ag.getAluno() == null || ag.getInstrutor() == null || ag.getAeronave() == null) {
            conflito_str += "Aluno, instrutor e aeronave são obrigatórios.\n";
            conflito = true;
        }
        if (ag.getHorario_partida().before(new Date())) {
            conflito_str += "O horário de partida não pode ser no passado.\n";
            conflito = true;
        }

        List<Agendamento> agendamentosExistentes = AgendamentoController.listarAgendamentos();

        for (Agendamento outro : agendamentosExistentes) {
            if (outro.getId().equals(ag.getId())) continue;

            // Conflito de horário - aluno
            if (outro.getAluno() != null && ag.getAluno() != null
                    && outro.getAluno().getCpf().equals(ag.getAluno().getCpf())) {
                Date existenteInicio = outro.getHorario_partida();
                Date existenteFim = outro.getHorario_retorno() != null ? outro.getHorario_retorno() : existenteInicio;
                Date novoInicio = ag.getHorario_partida();
                Date novoFim = ag.getHorario_retorno();

                if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                    conflito = true;
                    conflito_str += "Já existe um agendamento para este aluno dentro do período selecionado.\n";
                }
            }

            // Conflito de horário - instrutor
            if (outro.getInstrutor() != null && ag.getInstrutor() != null
                    && outro.getInstrutor().getCpf().equals(ag.getInstrutor().getCpf())) {
                Date existenteInicio = outro.getHorario_partida();
                Date existenteFim = outro.getHorario_retorno() != null ? outro.getHorario_retorno() : existenteInicio;
                Date novoInicio = ag.getHorario_partida();
                Date novoFim = ag.getHorario_retorno();

                if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                    conflito = true;
                    conflito_str += "Já existe um agendamento para este instrutor dentro do período selecionado.\n";
                }
            }

            // Conflito de horário - aeronave
            if (outro.getAeronave() != null && ag.getAeronave() != null
                    && outro.getAeronave().getMatricula().equals(ag.getAeronave().getMatricula())) {
                Date existenteInicio = outro.getHorario_partida();
                Date existenteFim = outro.getHorario_retorno() != null ? outro.getHorario_retorno() : existenteInicio;
                Date novoInicio = ag.getHorario_partida();
                Date novoFim = ag.getHorario_retorno();

                if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                    conflito = true;
                    conflito_str += "Existe agendamento da mesma aeronave dentro do período de partida e retorno.\n";
                }
            }

            if (conflito) break;
        }

        // Verifica horas de voo do aluno
        Aluno aluno = AlunoController.buscarId(ag.getAluno().getCpf());
        if (aluno.getHoras_compradas() <= 0) {
            conflito = true;
            conflito_str += "O aluno não possui horas de voo compradas.\n";
        }

        // Aviso de aeronave não homologada
        if (!conflito) {
            Aeronave aeronave = AeronaveController.buscarId(ag.getAeronave().getMatricula());
            int hora = ag.getHorario_partida().getHours();
            if ("VFR-D".equals(aeronave.getHabilitacao()) && (hora >= 18 || hora < 6)) {
                warn = true;
                warn_str += "Aeronave não homologada para o horário selecionado, confirme seus dados antes de prosseguir\n";
            }
        }

        //
        // RETORNA CONFLITOS OU AVISOS
        //
        if (conflito) return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        if (warn && !forcar) return ResponseEntity.ok(Map.of("warn", warn_str));

        //
        // ATUALIZAÇÃO DO AGENDAMENTO
        //
        ag.setId(UUID.fromString(id));
        AgendamentoController.atualizarAgendamento(ag);

        return ResponseEntity.ok(Map.of("status", "sucesso", "agendamento", ag.getId()));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        //
        // BUSCA AGENDAMENTO EXISTENTE
        //
        Agendamento ag = AgendamentoController.buscarId(id);
        if (ag == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado");
        }

        //
        // DELETA AGENDAMENTO
        //
        AgendamentoController.deletarAgendamento(ag.getId().toString());
    }
}
