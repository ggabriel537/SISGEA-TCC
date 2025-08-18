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

import com.sisgea.BancoDados.Controllers.DiarioBordoController;
import com.sisgea.Entidades.DiarioBordo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/diarios-bordo")
public class DiarioBordoAPI {
    @GetMapping("/{id}") 
    public DiarioBordo buscarId(@PathVariable String id) {
        DiarioBordo d = DiarioBordoController.buscarId(id);
        if (d == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diário de Bordo não encontrado");
        }
        return d;
    }
    
    @GetMapping 
    public List<DiarioBordo> listar() { 
        return DiarioBordoController.listarDiariosBordo(); 
    }
    
    @PostMapping 
    public DiarioBordo criar(@RequestBody DiarioBordo d) { 
        DiarioBordoController.salvarDiarioBordo(d); 
        return d; 
    }
    
    @PutMapping("/{id}") 
    public DiarioBordo atualizar(@PathVariable String id, @RequestBody DiarioBordo d) {
        DiarioBordo existente = DiarioBordoController.buscarId(id);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diário de Bordo não encontrado");
        }
        d.setId(UUID.fromString(id));
        DiarioBordoController.atualizarDiarioBordo(d);
        return d;
    }
    
    @DeleteMapping("/{id}") public void deletar(@PathVariable String id) {
        DiarioBordo d = DiarioBordoController.buscarId(id);
        if (d == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diário de Bordo não encontrado");
        }
        DiarioBordoController.deletarDiarioBordo(d);
    }
}
