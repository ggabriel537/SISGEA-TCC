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

import com.sisgea.BancoDados.Controllers.AeronaveController;
import com.sisgea.Entidades.Aeronave;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/aeronaves")
public class AeronaveAPI {
    @GetMapping("/{id}")
    public Aeronave buscarId(@PathVariable String id) {
        Aeronave ar = AeronaveController.buscarId(id);
        if (ar == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aeronave não encontrada");
        }
        return ar;
    }

    @GetMapping
    public List<Aeronave> listar() {
        return AeronaveController.listarAeronaves();
    }

    @PostMapping
    public Aeronave criar(@RequestBody Aeronave ar) {
        AeronaveController.salvarAeronave(ar);
        return ar;
    }

    @PutMapping("/{id}")
    public Aeronave atualizar(@PathVariable String id, @RequestBody Aeronave ar) {
        Aeronave existente = AeronaveController.buscarId(id);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aeronave não encontrada");
        }
        AeronaveController.atualizarAeronave(ar);
        return ar;
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        Aeronave ar = AeronaveController.buscarId(id);
        if (ar == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aeronave não encontrada");
        }
        AeronaveController.deletarAeronave(ar);
    }
}
