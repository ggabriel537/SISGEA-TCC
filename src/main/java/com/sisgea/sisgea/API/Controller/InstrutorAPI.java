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

import com.sisgea.BancoDados.Controllers.InstrutorController;
import com.sisgea.Entidades.Instrutor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/instrutores")
public class InstrutorAPI {
    
    @GetMapping("/{id}") 
    public Instrutor buscarId(@PathVariable String id) {
        Instrutor instrutor = InstrutorController.buscarId(id);
        if (instrutor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instrutor não encontrado");
        }
        return instrutor;
    }

    @GetMapping 
    public List<Instrutor> listar() { 
        return InstrutorController.listarInstrutores(); 
    }

    @PostMapping 
    public Instrutor criar(@RequestBody Instrutor instrutor) { 
        InstrutorController.salvarInstrutor(instrutor); 
        return instrutor; 
    }

    @PutMapping("/{id}") 
    public Instrutor atualizar(@PathVariable String id, @RequestBody Instrutor instrutor) {
        Instrutor existente = InstrutorController.buscarId(id);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instrutor não encontrado");
        }
        instrutor.setUsuario(id);
        InstrutorController.atualizarInstrutor(instrutor);
        return instrutor;
    }

    @DeleteMapping("/{id}") 
    public void deletar(@PathVariable String id) {
        Instrutor instrutor = InstrutorController.buscarId(id);
        if (instrutor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instrutor não encontrado");
        }
        InstrutorController.deletarInstrutor(instrutor);
    }
}
