package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sisgea.BancoDados.Controllers.AgendamentoController;
import com.sisgea.Entidades.Agendamento;

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
    public Agendamento criar(@RequestBody Agendamento ag) {
        AgendamentoController.salvarAgendamento(ag);
        return ag;
    }

    @PutMapping("/{id}")
    public Agendamento atualizar(@PathVariable String id, @RequestBody Agendamento ag) {
        Agendamento existente = AgendamentoController.buscarId(id);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado");
        }
        ag.setId(UUID.fromString(id));
        AgendamentoController.atualizarAgendamento(ag);
        return ag;
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        Agendamento ag = AgendamentoController.buscarId(id);
        if (ag == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado");
        }
        AgendamentoController.deletarAgendamento(ag);
    }
}
