package com.sisgea.sisgea.API.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sisgea.BancoDados.Controllers.AlunoController;
import com.sisgea.Entidades.Aluno;

import java.util.*;

@RestController
@RequestMapping("/api/alunos")
public class AlunoAPI {

    @GetMapping
    public List<Aluno> listar() {
        // Lista todos os alunos
        return AlunoController.listarAlunos();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> buscar(@PathVariable String cpf) {
        // Busca aluno pelo CPF
        Aluno aluno = AlunoController.buscarId(cpf);
        if (aluno == null) {
            // Retorna 404 se não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Aluno não encontrado"));
        }
        return ResponseEntity.ok(aluno);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Aluno aluno,
                                   @RequestParam(defaultValue = "false") boolean forcar) {

        //
        // BUSCA DE ALUNOS EXISTENTES PARA VALIDAÇÃO
        //
        List<Aluno> alunosExistentes;
        try {
            alunosExistentes = AlunoController.listarAlunos();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao listar alunos existentes para validação: " + e.getMessage()));
        }

        //
        // INICIALIZAÇÃO DE VARIÁVEIS DE CONFLITO E WARNING
        //
        String conflito_str = "";
        String warn_str = "";
        boolean warn = false;
        boolean conflito = false;

        //
        // VALIDAÇÃO DE CAMPOS OBRIGATÓRIOS
        //
        String erro = validarAluno(aluno);
        if (!erro.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erro));
        }

        //
        // VERIFICA CONFLITOS COM ALUNOS EXISTENTES (CPF e CANAC)
        //
        for (Aluno existente : alunosExistentes) {
            if (existente.getCpf().equals(aluno.getCpf())) {
                conflito = true;
                conflito_str += "Já existe um aluno com este CPF.\n";
                break;
            }
            if (existente.getCanac() != null && aluno.getCanac() != null &&
                existente.getCanac().equals(aluno.getCanac())) {
                conflito = true;
                conflito_str += "Já existe um aluno com este CANAC.\n";
                break;
            }
        }

        //
        // WARNINGS (valida email)
        //
        if (!conflito) {
            if (aluno.getEmail() != null && !aluno.getEmail().isBlank() && !aluno.getEmail().contains("@")) {
                warn = true;
                warn_str += "Email pode estar incorreto, confirme antes de prosseguir.\n";
            }
        }

        //
        // BLOQUEIA CADASTRO SE HOUVER CONFLITO
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
        // CADASTRO DO ALUNO
        //
        AlunoController.salvarAluno(aluno);
        return ResponseEntity.ok(Map.of("status", "sucesso", "cpf", aluno.getCpf()));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<?> atualizar(@PathVariable String cpf, @RequestBody Aluno aluno,
                                       @RequestParam(defaultValue = "false") boolean forcar) {

        // Busca aluno existente pelo CPF
        Aluno existente = AlunoController.buscarId(cpf);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Aluno não encontrado"));
        }

        //
        // INICIALIZAÇÃO DE VARIÁVEIS DE CONFLITO E WARNING
        //
        String conflito_str = "";
        String warn_str = "";
        boolean warn = false;
        boolean conflito = false;

        //
        // VALIDAÇÃO DE CAMPOS OBRIGATÓRIOS
        //
        String erro = validarAluno(aluno);
        if (!erro.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erro));
        }

        //
        // WARNINGS (valida email)
        //
        if (!conflito) {
            if (aluno.getEmail() != null && !aluno.getEmail().isBlank() && !aluno.getEmail().contains("@")) {
                warn = true;
                warn_str += "Email pode estar incorreto, confirme antes de prosseguir.\n";
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
        // ATUALIZAÇÃO DO ALUNO
        //
        aluno.setCpf(cpf);
        AlunoController.atualizarAluno(aluno);
        return ResponseEntity.ok(Map.of("status", "sucesso", "cpf", aluno.getCpf()));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deletar(@PathVariable String cpf) {
        // Busca aluno pelo CPF
        Aluno aluno = AlunoController.buscarId(cpf);
        if (aluno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Aluno não encontrado"));
        }

        //
        // DELETE DO ALUNO
        //
        AlunoController.deletarAluno(aluno);
        return ResponseEntity.ok(Map.of("status", "Aluno removido com sucesso"));
    }

    private String validarAluno(Aluno aluno) {
        // Valida campos obrigatórios do aluno
        String msg = "";
        if (aluno.getNome() == null || aluno.getNome().isBlank()) {
            msg += "Nome é obrigatório.\n";
        }
        if (aluno.getCpf() == null || aluno.getCpf().isBlank()) {
            msg += "CPF é obrigatório.\n";
        }
        if (aluno.getCanac() == null || aluno.getCanac().toString().isBlank()) {
            msg += "CANAC é obrigatório.\n";
        }
        if (aluno.getCurso() == null || aluno.getCurso() == "Selecione..." || aluno.getCurso().isBlank()) {
            msg += "Curso é obrigatório.\n";
        }
        if (aluno.getEmail() == null || aluno.getEmail().isBlank()) {
            msg += "Email é obrigatório.\n";
        }
        if (aluno.getTelefone() == null || aluno.getTelefone().isBlank()) {
            msg += "Telefone é obrigatório.\n";
        }
        return msg;
    }
}
