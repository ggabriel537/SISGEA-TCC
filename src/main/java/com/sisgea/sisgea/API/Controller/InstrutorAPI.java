package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sisgea.BancoDados.Controllers.InstrutorController;
import com.sisgea.Entidades.Instrutor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/instrutores")
public class InstrutorAPI {

    @GetMapping("/{cpf}")
    public ResponseEntity<?> buscar(@PathVariable String cpf) {
        Instrutor i = InstrutorController.buscarId(cpf);
        if (i == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Instrutor não encontrado"));
        }
        return ResponseEntity.ok(i);
    }

    @GetMapping
    public ResponseEntity<List<Instrutor>> listar() {
        return ResponseEntity.ok(InstrutorController.listarInstrutores());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Instrutor i, @RequestParam(defaultValue = "false") boolean forcar) {
        // Limitações de Cadastro dos instrutores
        List<Instrutor> instrutoresExistentes = null;
        try {
            instrutoresExistentes = InstrutorController.listarInstrutores();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao listar instrutores existentes para validação: " + e.getMessage()));
        }

        // Conflito -> Bloqueia o cadastro
        // Warn -> Apenas avisa o usuário, mas permite o cadastro
        String conflito_str = "";
        String warn_str = "";
        boolean warn = false;
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //
        if (i.getNome() == null || i.getNome().isBlank()) {
            conflito = true;
            conflito_str += "Nome é obrigatório.\n";
        }
        if (i.getCpf() == null || i.getCpf().isBlank()) {
            conflito = true;
            conflito_str += "CPF é obrigatório.\n";
        }
        if (i.getEmail() == null || i.getEmail().isBlank()) {
            conflito = true;
            conflito_str += "Email é obrigatório.\n";
        }
        if (i.getUsuario() == null) {
            conflito = true;
            conflito_str += "Usuário é obrigatório.\n";
        }

        //
        // CONFLITOS
        //
        for (Instrutor existente : instrutoresExistentes) {
            if (existente.getCpf().equals(i.getCpf())) {
                conflito = true;
                conflito_str += "Já existe um instrutor com este CPF.\n";
                break;
            }
        }

        //
        // WARNINGS
        //
        if (!conflito) {
            if (i.getEmail() != null && !i.getEmail().contains("@")) {
                warn = true;
                warn_str += "Email pode estar incorreto, confirme antes de prosseguir.\n";
            }
        }

        // Se houver conflito, bloqueia o cadastro
        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        // Se houver apenas warn, avisa o usuário, mas permite o cadastro
        if (warn && !forcar) {
            return ResponseEntity.ok(Map.of("warn", warn_str));
        }

        //
        // CADASTRO DO INSTRUTOR
        //
        InstrutorController.salvarInstrutor(i);
        return ResponseEntity.ok(Map.of("status", "sucesso", "cpf", i.getCpf()));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<?> atualizar(@PathVariable String cpf, @RequestBody Instrutor i,
                                       @RequestParam(defaultValue = "false") boolean forcar) {
        // Busca instrutor existente
        Instrutor existente = InstrutorController.buscarId(cpf);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Instrutor não encontrado"));
        }

        // Lista todos os instrutores para validação
        List<Instrutor> instrutoresExistentes = null;
        try {
            instrutoresExistentes = InstrutorController.listarInstrutores();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao listar instrutores existentes para validação: " + e.getMessage()));
        }

        // Inicializa variáveis de conflito e warning
        String conflito_str = "";
        String warn_str = "";
        boolean warn = false;
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //
        if (i.getNome() == null || i.getNome().isBlank()) {
            conflito = true;
            conflito_str += "Nome é obrigatório.\n";
        }
        if (i.getCpf() == null || i.getCpf().isBlank()) {
            conflito = true;
            conflito_str += "CPF é obrigatório.\n";
        }
        if (i.getEmail() == null || i.getEmail().isBlank()) {
            conflito = true;
            conflito_str += "Email é obrigatório.\n";
        }
        if (i.getUsuario() == null) {
            conflito = true;
            conflito_str += "Usuário é obrigatório.\n";
        }

        //
        // CONFLITOS
        //
        for (Instrutor outro : instrutoresExistentes) {
            if (outro.getCpf().equals(i.getCpf()) && !outro.getCpf().equals(cpf)) {
                conflito = true;
                conflito_str += "Já existe um instrutor com este CPF.\n";
                break;
            }
        }

        //
        // WARNINGS
        //
        if (!conflito) {
            if (i.getEmail() != null && !i.getEmail().contains("@")) {
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
        // ATUALIZAÇÃO DO INSTRUTOR
        //
        i.setCpf(cpf);
        InstrutorController.atualizarInstrutor(i);
        return ResponseEntity.ok(Map.of("status", "sucesso", "cpf", i.getCpf()));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deletar(@PathVariable String cpf) {
        Instrutor i = InstrutorController.buscarId(cpf);
        if (i == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Instrutor não encontrado"));
        }
        InstrutorController.deletarInstrutor(cpf);
        return ResponseEntity.ok(Map.of("status", "deletado", "cpf", cpf));
    }
}
