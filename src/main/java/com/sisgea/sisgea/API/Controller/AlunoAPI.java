package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.sisgea.BancoDados.Controllers.AlunoController;
import com.sisgea.Entidades.Aluno;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/alunos")
public class AlunoAPI {

    @GetMapping("/{cpf}")
    public Aluno buscar(@PathVariable String cpf) {
        Aluno aluno = AlunoController.buscarId(cpf);
        if (aluno == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        return aluno;
    }

    @GetMapping
    public List<Aluno> listar() {
        return AlunoController.listarAlunos();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Aluno aluno) {
        // Limitações de Cadastro dos alunos
        // Conflito -> Bloqueia o cadastro
        String conflito_str = "";
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //
        String erro = validarAluno(aluno);
        if (!erro.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erro));
        }

        //
        // CONFLITOS
        //
        // CPF é chave natural única
        if (AlunoController.buscarId(aluno.getCpf()) != null) {
            conflito = true;
            conflito_str += "Já existe um aluno com este CPF.\n";
        }

        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        //
        // CADASTRO DO ALUNO
        //
        AlunoController.salvarAluno(aluno);
        return ResponseEntity.ok(Map.of("status", "sucesso", "cpf", aluno.getCpf()));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<?> atualizar(@PathVariable String cpf, @RequestBody Aluno aluno) {
        // Busca aluno existente
        Aluno existente = AlunoController.buscarId(cpf);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Aluno não encontrado"));
        }

        // DADOS OBRIGATÓRIOS
        String erro = validarAluno(aluno);
        if (!erro.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erro));
        }

        //
        // ATUALIZAÇÃO DO ALUNO
        //
        aluno.setCpf(cpf);
        AlunoController.atualizarAluno(aluno);
        return ResponseEntity.ok(Map.of("status", "sucesso", "cpf", aluno.getCpf()));
    }

    @DeleteMapping("/{cpf}")
    public void deletar(@PathVariable String cpf) {
        Aluno aluno = AlunoController.buscarId(cpf);
        if (aluno == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        AlunoController.deletarAluno(aluno);
    }

    private String validarAluno(Aluno aluno) {
        String msg = "";
        if (aluno.getNome() == null || aluno.getNome().isBlank()) msg += "Nome é obrigatório. ";
        if (aluno.getCpf() == null || aluno.getCpf().isBlank()) msg += "CPF é obrigatório.";
        return msg;
    }
}
