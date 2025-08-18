package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.UUID;

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

import com.sisgea.BancoDados.Controllers.ManutencaoController;
import com.sisgea.Entidades.Manutencao;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/manutencoes")
public class ManutencaoAPI {
    @GetMapping("/{id}")
    public Manutencao buscarId(@PathVariable String id) {
        Manutencao manutencao = ManutencaoController.buscarId(id);
        if (manutencao == null) {
            throw new ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Manutenção não encontrada"
            );
        }
        return manutencao;
    }

    @GetMapping
    public List<Manutencao> listar() {
        return ManutencaoController.listarManutencoes();
    }

    @PostMapping
    public Manutencao criar(@RequestBody Manutencao manutencao) {
        ManutencaoController.salvarManutencao(manutencao);
        return manutencao;
    }

    @PutMapping("/{id}")
    public Manutencao atualizar(@PathVariable String id, @RequestBody Manutencao manutencao) {
        Manutencao existente = ManutencaoController.buscarId(id);
        if (existente == null) {
            throw new ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Manutenção não encontrada"
            );
        }
        UUID uuid = UUID.fromString(id);
        manutencao.setId(uuid);
        ManutencaoController.atualizarManutencao(manutencao);
        return manutencao;
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        Manutencao manutencao = ManutencaoController.buscarId(id);
        if (manutencao == null) {
            throw new ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Manutencao não encontrada"
            );
        }
        ManutencaoController.deletarManutencao(manutencao);
    }
}
