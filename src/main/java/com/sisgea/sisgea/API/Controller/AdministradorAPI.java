package com.sisgea.sisgea.API.Controller;

import java.util.List;

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

import com.sisgea.BancoDados.Controllers.AdministradorController;
import com.sisgea.Entidades.Administrador;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/administradores")
public class AdministradorAPI {
    @GetMapping("/{usuario}")
    public Administrador buscarId(@PathVariable String usuario) {
        Administrador adm = AdministradorController.buscarId(usuario);
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
    public Administrador criar(@RequestBody Administrador adm) {
        AdministradorController.salvarAdministrador(adm);
        return adm;
    }

    @PutMapping("/{usuario}")
    public Administrador atualizar(@PathVariable String usuario, @RequestBody Administrador adm) {
        Administrador existente = AdministradorController.buscarId(usuario);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrador não encontrado");
        }
        AdministradorController.atualizarAdministrador(adm);
        return adm;
    }

    @DeleteMapping("/{usuario}")
    public void deletar(@PathVariable String usuario) {
        Administrador adm = AdministradorController.buscarId(usuario);
        if (adm == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrador não encontrado");
        }
        AdministradorController.deletarAdministrador(adm);
    }
}
