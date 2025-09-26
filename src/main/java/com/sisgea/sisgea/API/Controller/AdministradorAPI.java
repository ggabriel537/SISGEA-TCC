package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.sisgea.BancoDados.Controllers.AdministradorController;
import com.sisgea.BancoDados.Controllers.InstrutorController;
import com.sisgea.Entidades.Administrador;
import com.sisgea.Entidades.Instrutor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/administradores")
public class AdministradorAPI {

    @GetMapping("/{id}")
    public Administrador buscar(@PathVariable String id) {
        Administrador adm = AdministradorController.buscarId(id);
        if (adm == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrador não encontrado");
        }
        return adm;
    }

    @GetMapping
    public List<Administrador> listar() {
        return AdministradorController.listarAdministradores();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Administrador adm) {
        List<Administrador> admins = AdministradorController.listarAdministradores();
        List<Instrutor> instrutores = InstrutorController.listarInstrutores();

        String conflito_str = "";
        boolean conflito = false;

        // DADOS OBRIGATÓRIOS
        conflito_str += validarAdministrador(adm);
        if (!conflito_str.isEmpty()) {
            conflito = true;
        }

        // CONFLITOS
        if (adm.getUsuario() != null) {
            if (admins != null && !admins.isEmpty()) {
                for (Administrador a : admins) {
                    if (adm.getUsuario().getUsuario() != null && !adm.getUsuario().getUsuario().isBlank()
                            && a.getUsuario() != null
                            && a.getUsuario().getUsuario().equals(adm.getUsuario().getUsuario())) {
                        conflito = true;
                        conflito_str += "Já existe um administrador com este usuário.\n";
                    }
                }
            }

            if (instrutores != null && !instrutores.isEmpty()) {
                for (Instrutor i : instrutores) {
                    if (adm.getUsuario().getUsuario() != null && !adm.getUsuario().getUsuario().isBlank()
                            && i.getUsuario() != null
                            && i.getUsuario().getUsuario().equals(adm.getUsuario().getUsuario())) {
                        conflito = true;
                        conflito_str += "Já existe um instrutor com este usuário.\n";
                    }
                }
            }

            if (adm.getUsuario().getUsuario() == null || adm.getUsuario().getUsuario().isBlank()) {
                conflito = true;
                conflito_str += "Usuário é obrigatório.\n";
            }
            if (adm.getUsuario().getSenha() == null || adm.getUsuario().getSenha().isBlank()) {
                conflito = true;
                conflito_str += "Senha é obrigatória.\n";
            }
        } else {
            conflito = true;
            conflito_str += "Usuário e senha são obrigatórios.\n";
        }

        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        AdministradorController.salvarAdministrador(adm);
        return ResponseEntity.ok(Map.of("status", "sucesso", "id", adm.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody Administrador adm) {
        String conflito_str = "";
        boolean conflito = false;

        Administrador existente = AdministradorController.buscarId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Administrador não encontrado"));
        }

        conflito_str += validarAdministrador(adm);
        if (!conflito_str.isEmpty()) {
            conflito = true;
        }

        List<Administrador> admins = AdministradorController.listarAdministradores();
        List<Instrutor> instrutores = InstrutorController.listarInstrutores();

        if (adm.getUsuario() != null) {
            if (admins != null && !admins.isEmpty()) {
                for (Administrador a : admins) {
                    if (a.getId() != null && !a.getId().equals(id)
                            && adm.getUsuario().getUsuario() != null && !adm.getUsuario().getUsuario().isBlank()
                            && a.getUsuario() != null
                            && a.getUsuario().getUsuario().equals(adm.getUsuario().getUsuario())) {
                        conflito = true;
                        conflito_str += "Já existe um administrador com este usuário.\n";
                    }
                }
            }

            if (instrutores != null && !instrutores.isEmpty()) {
                for (Instrutor i : instrutores) {
                    if (adm.getUsuario().getUsuario() != null && !adm.getUsuario().getUsuario().isBlank()
                            && i.getUsuario() != null
                            && i.getUsuario().getUsuario().equals(adm.getUsuario().getUsuario())) {
                        conflito = true;
                        conflito_str += "Já existe um instrutor com este usuário.\n";
                    }
                }
            }
        }

        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        adm.setId(id);
        if (adm.getUsuario() == null
                || adm.getUsuario().getUsuario() == null || adm.getUsuario().getUsuario().isBlank()
                || adm.getUsuario().getSenha() == null || adm.getUsuario().getSenha().isBlank()) {
            adm.setUsuario(existente.getUsuario());
        }

        AdministradorController.atualizarAdministrador(adm);
        return ResponseEntity.ok(Map.of("status", "sucesso", "id", adm.getId()));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        Administrador adm = AdministradorController.buscarId(id);
        if (adm == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrador não encontrado");
        }
        AdministradorController.deletarAdministrador(adm);
    }

    private String validarAdministrador(Administrador adm) {
        String msg = "";
        if (adm.getNome() == null || adm.getNome().isBlank())
            msg += "Nome do administrador é obrigatório.\n";
        return msg;
    }
}
