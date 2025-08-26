package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.sisgea.BancoDados.Controllers.AdministradorController;
import com.sisgea.Entidades.Administrador;

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
        // Limitações de Cadastro dos administradores
        // Conflito -> Bloqueia o cadastro
        String conflito_str = "";
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //
        String erro = validarAdministrador(adm);
        if (!erro.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erro));
        }

        //
        // CONFLITOS
        //
        // Se vier ID preenchido e já existir, é duplicidade
        if (adm.getId() != null && !adm.getId().isBlank()
                && AdministradorController.buscarId(adm.getId()) != null) {
            conflito = true;
            conflito_str += "Já existe um administrador com este ID.\n";
        }

        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        //
        // CADASTRO DO ADMINISTRADOR
        //
        AdministradorController.salvarAdministrador(adm);
        return ResponseEntity.ok(Map.of("status", "sucesso", "id", adm.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody Administrador adm) {
        // Busca administrador existente
        Administrador existente = AdministradorController.buscarId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Administrador não encontrado"));
        }

        // DADOS OBRIGATÓRIOS
        String erro = validarAdministrador(adm);
        if (!erro.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erro));
        }

        //
        // ATUALIZAÇÃO DO ADMINISTRADOR
        //
        adm.setId(id);
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
        if (adm.getNome() == null || adm.getNome().isBlank()) msg += "Nome do administrador é obrigatório.";
        return msg;
    }
}
