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

import com.sisgea.BancoDados.Controllers.EnderecoController;
import com.sisgea.Entidades.Endereco;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/enderecos")
public class EnderecoAPI {

    @GetMapping("/{id}") 
    public Endereco buscarId(@PathVariable String id) {
        Endereco endereco = EnderecoController.buscarId(id);
        if (endereco == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }
        return endereco;
    }
    
    @GetMapping 
    public List<Endereco> listar() {
        return EnderecoController.listarEnderecos(); 
    }
    
    @PostMapping 
    public Endereco criar(@RequestBody Endereco endereco) 
    { 
        EnderecoController.salvarEndereco(endereco); 
        return endereco; 
    }
    
    @PutMapping("/{id}") 
    public Endereco atualizar(@PathVariable String id, @RequestBody Endereco endereco) {
        Endereco existente = EnderecoController.buscarId(id);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }
        endereco.setId(UUID.fromString(id));
        EnderecoController.atualizarEndereco(endereco);
        return endereco;
    }

    @DeleteMapping("/{id}") 
    public void deletar(@PathVariable String id) {
        Endereco endereco = EnderecoController.buscarId(id);
        if (endereco == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }
        EnderecoController.deletarEndereco(endereco);
    }
}
