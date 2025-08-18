package com.sisgea.sisgea.API.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sisgea.BancoDados.Controllers.AlunoController;
import com.sisgea.Entidades.Aluno;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/alunos")
public class AlunoAPI {
    @GetMapping("/{id}")
    public Aluno buscarId(@PathVariable String id) {
        Aluno a = AlunoController.buscarId(id);
        if (a == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        return a;
    }

    @GetMapping
    public List<Aluno> listar() {
        return AlunoController.listarAlunos();
    }

    @PostMapping
    public Aluno criar(@RequestBody Aluno a) {
        AlunoController.salvarAluno(a);
        return a;
    }

    @PutMapping("/{id}")
    public Aluno atualizar(@PathVariable String id, @RequestBody Aluno a) {
        Aluno existente = AlunoController.buscarId(id);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        AlunoController.atualizarAluno(a);
        return a;
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String cpf) {
        Aluno a = AlunoController.buscarId(cpf);
        if (a == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        AlunoController.deletarAluno(a);
    }
}
