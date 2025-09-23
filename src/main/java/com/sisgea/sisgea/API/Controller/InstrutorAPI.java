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
        List<Instrutor> instrutoresExistentes;
        try {
            instrutoresExistentes = InstrutorController.listarInstrutores();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao listar instrutores existentes para validação: " + e.getMessage()));
        }

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
        if (i.getUsuario() == null || i.getUsuario().getUsuario() == null || i.getUsuario().getUsuario().isBlank()) {
            conflito = true;
            conflito_str += "Usuário é obrigatório.\n";
        }

        // Força sempre permissao = 0
        if (i.getUsuario() != null) {
            i.getUsuario().setPermissao(0);
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
            if (existente.getCanac() != null && i.getCanac() != null &&
                    existente.getCanac().equals(i.getCanac())) {
                conflito = true;
                conflito_str += "Já existe um instrutor com este CANAC.\n";
                break;
            }
            if (existente.getUsuario() != null && i.getUsuario() != null &&
                    existente.getUsuario().getUsuario().equals(i.getUsuario().getUsuario())) {
                conflito = true;
                conflito_str += "Já existe um instrutor com este Usuário.\n";
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

        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        if (warn && !forcar) {
            return ResponseEntity.ok(Map.of("warn", warn_str));
        }

        InstrutorController.salvarInstrutor(i);
        return ResponseEntity.ok(Map.of("status", "sucesso", "cpf", i.getCpf()));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<?> atualizar(@PathVariable String cpf, @RequestBody Instrutor i,
                                       @RequestParam(defaultValue = "false") boolean forcar) {
        Instrutor existente = InstrutorController.buscarId(cpf);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Instrutor não encontrado"));
        }

        List<Instrutor> instrutoresExistentes;
        try {
            instrutoresExistentes = InstrutorController.listarInstrutores();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao listar instrutores existentes para validação: " + e.getMessage()));
        }

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
        if (i.getUsuario() == null || i.getUsuario().getUsuario() == null || i.getUsuario().getUsuario().isBlank()) {
            conflito = true;
            conflito_str += "Usuário é obrigatório.\n";
        }

        // Força sempre permissao = 0
        if (i.getUsuario() != null) {
            i.getUsuario().setPermissao(0);
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
            if (outro.getCanac() != null && i.getCanac() != null &&
                    outro.getCanac().equals(i.getCanac()) && !outro.getCpf().equals(cpf)) {
                conflito = true;
                conflito_str += "Já existe um instrutor com este CANAC.\n";
                break;
            }
            if (outro.getUsuario() != null && i.getUsuario() != null &&
                    outro.getUsuario().getUsuario().equals(i.getUsuario().getUsuario()) &&
                    !outro.getCpf().equals(cpf)) {
                conflito = true;
                conflito_str += "Já existe um instrutor com este Usuário.\n";
                break;
            }
        }

        if (!conflito) {
            if (i.getEmail() != null && !i.getEmail().contains("@")) {
                warn = true;
                warn_str += "Email pode estar incorreto, confirme antes de prosseguir.\n";
            }
        }

        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        if (warn && !forcar) {
            return ResponseEntity.ok(Map.of("warn", warn_str));
        }

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
