package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.UUID;

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

import com.sisgea.BancoDados.Controllers.DiscrepanciaController;
import com.sisgea.Entidades.Discrepancia;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/discrepancias")
public class DiscrepanciaAPI {

    @GetMapping("/{id}") 
    public Discrepancia buscarId(@PathVariable String id) {
        Discrepancia d = DiscrepanciaController.buscarId(id);
        if (d == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discrepância não encontrada");
        }
        return d;
    }
    
    @GetMapping public List<Discrepancia> listar() { 
        return DiscrepanciaController.listarDiscrepancias(); 
    }
    
    @PostMapping 
    public Discrepancia criar(@RequestBody Discrepancia d) { 
        DiscrepanciaController.salvarDiscrepancia(d); 
        return d; 
    }
    
    @PutMapping("/{id}") public Discrepancia atualizar(@PathVariable String id, @RequestBody Discrepancia d) {
        Discrepancia existente = DiscrepanciaController.buscarId(id);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discrepância não encontrada");
        }
        d.setId(UUID.fromString(id));
        DiscrepanciaController.atualizarDiscrepancia(d);
        return d;
    }
    
    @DeleteMapping("/{id}") public void deletar(@PathVariable String id) {
        Discrepancia d = DiscrepanciaController.buscarId(id);
        if (d == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discrepância não encontrada");
        }
        DiscrepanciaController.deletarDiscrepancia(d);
    }
}
