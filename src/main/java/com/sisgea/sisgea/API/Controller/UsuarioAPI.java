package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sisgea.BancoDados.Controllers.UsuarioController;
import com.sisgea.Entidades.Usuario;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioAPI {

    @GetMapping("/{usuario}")
    public ResponseEntity<?> buscar(@PathVariable String usuario) {
        Usuario u = UsuarioController.buscarUsuario(usuario);
        if (u == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuário não encontrado"));
        }
        return ResponseEntity.ok(u);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(UsuarioController.listarUsuarios());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Usuario u, @RequestParam(defaultValue = "false") boolean forcar) {
        // Limitações de Cadastro dos usuários
        List<Usuario> usuariosExistentes = null;
        try {
            usuariosExistentes = UsuarioController.listarUsuarios();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao listar usuários existentes para validação: " + e.getMessage()));
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
        if (u.getUsuario() == null || u.getUsuario().isBlank()) {
            conflito = true;
            conflito_str += "Usuário é obrigatório.\n";
        }
        if (u.getSenha() == null || u.getSenha().isBlank()) {
            conflito = true;
            conflito_str += "Senha é obrigatória.\n";
        }
        if (u.getPermissao() == null) {
            conflito = true;
            conflito_str += "Permissão é obrigatória.\n";
        }

        //
        // CONFLITOS
        //
        for (Usuario existente : usuariosExistentes) {
            if (existente.getUsuario().equals(u.getUsuario())) {
                conflito = true;
                conflito_str += "Já existe um usuário com este username.\n";
                break;
            }
        }

        //
        // WARNINGS
        //
        if (!conflito) {
            if (u.getSenha() != null && u.getSenha().length() < 8) {
                warn = true;
                warn_str += "Senha fraca (menos de 8 caracteres), confirme antes de prosseguir.\n";
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
        // CADASTRO DO USUÁRIO
        //
        UsuarioController.salvarUsuario(u);
        return ResponseEntity.ok(Map.of("status", "sucesso", "usuario", u.getUsuario()));
    }

    @PutMapping("/{usuario}")
    public ResponseEntity<?> atualizar(@PathVariable String usuario, @RequestBody Usuario u,
                                       @RequestParam(defaultValue = "false") boolean forcar) {
        // Busca usuário existente
        Usuario existente = UsuarioController.buscarUsuario(usuario);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuário não encontrado"));
        }

        // Lista todos os usuários para validação
        List<Usuario> usuariosExistentes = null;
        try {
            usuariosExistentes = UsuarioController.listarUsuarios();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao listar usuários existentes para validação: " + e.getMessage()));
        }

        // Inicializa variáveis de conflito e warning
        String conflito_str = "";
        String warn_str = "";
        boolean warn = false;
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //
        if (u.getUsuario() == null || u.getUsuario().isBlank()) {
            conflito = true;
            conflito_str += "Usuário é obrigatório.\n";
        }
        if (u.getSenha() == null || u.getSenha().isBlank()) {
            conflito = true;
            conflito_str += "Senha é obrigatória.\n";
        }
        if (u.getPermissao() == null) {
            conflito = true;
            conflito_str += "Permissão é obrigatória.\n";
        }

        //
        // CONFLITOS
        //
        for (Usuario outro : usuariosExistentes) {
            if (outro.getUsuario().equals(u.getUsuario()) && !outro.getUsuario().equals(usuario)) {
                conflito = true;
                conflito_str += "Já existe um usuário com este username.\n";
                break;
            }
        }

        //
        // WARNINGS
        //
        if (!conflito) {
            if (u.getSenha() != null && u.getSenha().length() < 8) {
                warn = true;
                warn_str += "Senha fraca (menos de 8 caracteres), confirme antes de prosseguir.\n";
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
        // ATUALIZAÇÃO DO USUÁRIO
        //
        u.setUsuario(usuario);
        UsuarioController.atualizarUsuario(u);
        return ResponseEntity.ok(Map.of("status", "sucesso", "usuario", u.getUsuario()));
    }

    @DeleteMapping("/{usuario}")
    public ResponseEntity<?> deletar(@PathVariable String usuario) {
        Usuario u = UsuarioController.buscarUsuario(usuario);
        if (u == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuário não encontrado"));
        }
        UsuarioController.deletarUsuario(u);
        return ResponseEntity.ok(Map.of("status", "deletado", "usuario", usuario));
    }
}
