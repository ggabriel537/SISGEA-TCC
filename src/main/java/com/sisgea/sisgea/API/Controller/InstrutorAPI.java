package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sisgea.BancoDados.Controllers.InstrutorController;
import com.sisgea.BancoDados.Controllers.UsuarioController;
import com.sisgea.BancoDados.Controllers.AdministradorController;
import com.sisgea.Entidades.Instrutor;
import com.sisgea.Entidades.Usuario;
import com.sisgea.Entidades.Administrador;

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
    public ResponseEntity<?> criar(@RequestBody Instrutor i) {
        List<Instrutor> instrutores = InstrutorController.listarInstrutores();
        List<Administrador> administradores = AdministradorController.listarAdministradores();

        String conflito_str = "";
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //
        conflito_str += validarInstrutor(i);
        if (!conflito_str.isEmpty()) {
            conflito = true;
        }

        //
        // CONFLITOS
        //
        if (i.getUsuario() != null) {
            if (instrutores != null && !instrutores.isEmpty()) {
                for (Instrutor outro : instrutores) {
                    if (i.getCpf() != null && i.getCpf().equals(outro.getCpf())) {
                        conflito = true;
                        conflito_str += "Já existe um instrutor com este CPF.\n";
                    }
                    if (i.getCanac() != null && outro.getCanac() != null
                            && i.getCanac().equals(outro.getCanac())) {
                        conflito = true;
                        conflito_str += "Já existe um instrutor com este CANAC.\n";
                    }
                    if (i.getUsuario().getUsuario() != null && !i.getUsuario().getUsuario().isBlank()
                            && outro.getUsuario() != null
                            && outro.getUsuario().getUsuario().equals(i.getUsuario().getUsuario())) {
                        conflito = true;
                        conflito_str += "Já existe um instrutor com este usuário.\n";
                    }
                }
            }

            if (administradores != null && !administradores.isEmpty()) {
                for (Administrador adm : administradores) {
                    if (i.getUsuario().getUsuario() != null && !i.getUsuario().getUsuario().isBlank()
                            && adm.getUsuario() != null
                            && adm.getUsuario().getUsuario().equals(i.getUsuario().getUsuario())) {
                        conflito = true;
                        conflito_str += "Já existe um administrador com este usuário.\n";
                    }
                }
            }

            if (i.getUsuario().getUsuario() == null || i.getUsuario().getUsuario().isBlank()) {
                conflito = true;
                conflito_str += "Usuário é obrigatório.\n";
            }
            if (i.getUsuario().getSenha() == null || i.getUsuario().getSenha().isBlank()) {
                conflito = true;
                conflito_str += "Senha é obrigatória.\n";
            }
        } else {
            conflito = true;
            conflito_str += "Usuário e senha são obrigatórios.\n";
        }

        // Permissão sempre 0 para instrutor
        if (i.getUsuario() != null) {
            i.getUsuario().setPermissao(0);
        }

        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        InstrutorController.salvarInstrutor(i);
        return ResponseEntity.ok(Map.of("status", "sucesso", "cpf", i.getCpf()));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<?> atualizar(@PathVariable String cpf, @RequestBody Instrutor i) {
        Instrutor existente = InstrutorController.buscarId(cpf);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Instrutor não encontrado"));
        }

        // Validação dos dados básicos (exceto usuário)
        String conflito_str = validarInstrutor(i);
        if (!conflito_str.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        // Atualiza apenas os dados editáveis
        existente.setNome(i.getNome());
        existente.setTelefone(i.getTelefone());
        existente.setEmail(i.getEmail());
        existente.setHabilitacao(i.getHabilitacao());
        existente.setCanac(i.getCanac());
        existente.setEndereco(i.getEndereco());

        // Mantém o mesmo usuário (login) e só atualiza a senha
        Usuario user = existente.getUsuario();
        if (i.getUsuario() != null && i.getUsuario().getSenha() != null && !i.getUsuario().getSenha().isBlank()) {
            user.setSenha(i.getUsuario().getSenha());
        }
        user.setPermissao(0); // mantém permissão 0 para instrutor
        existente.setUsuario(user);

        UsuarioController.atualizarUsuario(user);
        InstrutorController.atualizarInstrutor(existente);

        return ResponseEntity.ok(Map.of("status", "sucesso", "cpf", existente.getCpf()));
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

    private String validarInstrutor(Instrutor i) {
        String msg = "";
        if (i.getNome() == null || i.getNome().isBlank())
            msg += "Nome do instrutor é obrigatório.\n";
        if (i.getCpf() == null || i.getCpf().isBlank())
            msg += "CPF é obrigatório.\n";
        if (i.getCanac() == null)
            msg += "CANAC é obrigatório.\n";
        if (i.getEmail() == null || i.getEmail().isBlank())
            msg += "Email é obrigatório.\n";
        return msg;
    }
}
