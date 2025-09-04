package com.sisgea.sisgea.API.Controller;

import java.util.Date;
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
    public Agendamento buscarId(@PathVariable String id) {
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
        // Limitações de Cadastro dos agendamentos
        List<Agendamento> agendamentosExistentes = null;
        try {
            agendamentosExistentes = AgendamentoController.listarAgendamentos();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao listar agendamentos existentes para validação: " + e.getMessage()));
        }
        // Conflito -> Bloqueia o cadastro do agendamento
        // Warn -> Apenas avisa o usuario, mas permite o cadastro do agendamento
        String conflito_str = "";
        String warn_str = "";
        boolean warn = false;
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //

        // Sem data de agendamento
        if (ag.getData_agendamento() == null) {
            conflito = true;
            conflito_str += "Data do agendamento é obrigatória.\n";
        }

        // Sem aluno, instrutor ou aeronave
        if (ag.getAluno() == null || ag.getInstrutor() == null || ag.getAeronave() == null) {
            conflito_str += "Aluno, instrutor e aeronave são obrigatórios.";
            conflito = true;
        }

        // Data do agendamento no passado
        if (ag.getData_agendamento().before(new Date())) {
            conflito_str += "A data do agendamento não pode ser no passado.";
            conflito = true;
        }

        //
        // CONFLITOS
        //

        // Validar novo cadastro de agendamento com o antigo
        for (Agendamento existente : agendamentosExistentes) {
            Aeronave aeronaveExistente = existente.getAeronave();
            Date dataExistente = existente.getData_agendamento();
            // Verifica se já existe um agendamento para a mesma aeronave na mesma data
            if (aeronaveExistente.getMatricula().equals(ag.getAeronave().getMatricula())
                    && dataExistente.equals(ag.getData_agendamento())) {
                conflito = true;
                conflito_str += "Já existe um agendamento para esta aeronave na mesma data.\n";
            }
            // Verifica se já existe um agendamento para o mesmo aluno na mesma data
            if (existente.getAluno().getCpf().equals(ag.getAluno().getCpf())
                    && existente.getData_agendamento().equals(ag.getData_agendamento())) {
                conflito = true;
                conflito_str += "Já existe um agendamento para este aluno na mesma data.\n";
            }
            // Verifica se já existe um agendamento para o mesmo instrutor na mesma data
            if (existente.getInstrutor().getCpf().equals(ag.getInstrutor().getCpf())
                    && existente.getData_agendamento().equals(ag.getData_agendamento())) {
                conflito = true;
                conflito_str += "Já existe um agendamento para este instrutor na mesma data.\n";
            }
            if (conflito) {
                break;
            }
        }

        // Verifica se o Aluno possui horas de voo compradas
        Aluno aluno = AlunoController.buscarId(ag.getAluno().getCpf());
        if (aluno.getHoras_compradas() <= 0) {
            conflito = true;
            conflito_str += "O aluno não possui horas de voo compradas.\n";
        }

        //
        // WARNINGS
        //

        if (!conflito) {
            // Validar se o aviao pode voar no horario do agendamento
            // VRF-D não pode voar noturno, Visual Flight Rules - D Representando apenas voo
            // diurno
            Aeronave aeronave = AeronaveController.buscarId(ag.getAeronave().getMatricula());
            if (aeronave.getHabilitacao().equals("VFR-D")
                    && (ag.getData_agendamento().getHours() >= 18 || ag.getData_agendamento().getHours() < 6)) {
                warn_str += "Aeronave não homologada para o horário selecionado, confirme seus dados antes de prosseguir\n";
                warn = true;
            }
        }

        // Se houver conflito, bloqueia o cadastro do agendamento
        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        // Se houver apenas warn, avisa o usuario, mas permite o cadastro do agendamento
        if (warn && !forcar) {
            // Vai enviar o aviso ao usuario para que ele confirme o cadastro
            return ResponseEntity.ok(Map.of("warn", warn_str));
        }

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
        // Busca agendamento existente
        Agendamento existente = AgendamentoController.buscarId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Agendamento não encontrado"));
        }

        // Lista todos os agendamentos para validação
        List<Agendamento> agendamentosExistentes = AgendamentoController.listarAgendamentos();

        // Inicializa variáveis de conflito e warning
        String conflito_str = "";
        String warn_str = "";
        boolean warn = false;
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //

        // Sem data de agendamento
        if (ag.getData_agendamento() == null) {
            conflito = true;
            conflito_str += "Data do agendamento é obrigatória.\n";
        }

        // Sem aluno, instrutor ou aeronave
        if (ag.getAluno() == null || ag.getInstrutor() == null || ag.getAeronave() == null) {
            conflito = true;
            conflito_str += "Aluno, instrutor e aeronave são obrigatórios.\n";
        }

        // Data do agendamento no passado
        if (ag.getData_agendamento() != null && ag.getData_agendamento().before(new Date())) {
            conflito = true;
            conflito_str += "A data do agendamento não pode ser no passado.\n";
        }

        //
        // CONFLITOS
        //

        // Verifica conflito com outros agendamentos existentes
        for (Agendamento outro : agendamentosExistentes) {
            // Ignora o próprio agendamento que está sendo atualizado
            if (outro.getId().equals(ag.getId()))
                continue;

            // Conflito com mesma aeronave na mesma data
            if (outro.getAeronave().getMatricula().equals(ag.getAeronave().getMatricula())
                    && outro.getData_agendamento().equals(ag.getData_agendamento())) {
                conflito = true;
                conflito_str += "Já existe um agendamento para esta aeronave na mesma data.\n";
            }

            // Conflito com mesmo aluno na mesma data
            if (outro.getAluno().getCpf().equals(ag.getAluno().getCpf())
                    && outro.getData_agendamento().equals(ag.getData_agendamento())) {
                conflito = true;
                conflito_str += "Já existe um agendamento para este aluno na mesma data.\n";
            }

            // Conflito com mesmo instrutor na mesma data
            if (outro.getInstrutor().getCpf().equals(ag.getInstrutor().getCpf())
                    && outro.getData_agendamento().equals(ag.getData_agendamento())) {
                conflito = true;
                conflito_str += "Já existe um agendamento para este instrutor na mesma data.\n";
            }

            if (conflito)
                break;
        }

        // Verifica se o aluno possui horas de voo compradas
        Aluno aluno = AlunoController.buscarId(ag.getAluno().getCpf());
        if (aluno.getHoras_compradas() <= 0) {
            conflito = true;
            conflito_str += "O aluno não possui horas de voo compradas.\n";
        }

        //
        // WARNINGS
        //

        if (!conflito) {
            // Validar se o avião pode voar no horário do agendamento
            // VRF-D não pode voar noturno, Visual Flight Rules - D representando voo diurno
            Aeronave aeronave = AeronaveController.buscarId(ag.getAeronave().getMatricula());
            if (aeronave.getHabilitacao().equals("VFR-D")
                    && (ag.getData_agendamento().getHours() >= 18 || ag.getData_agendamento().getHours() < 6)) {
                warn_str += "Aeronave não homologada para o horário selecionado, confirme seus dados antes de prosseguir\n";
                warn = true;
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
        // ATUALIZAÇÃO DO AGENDAMENTO
        //

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
}
