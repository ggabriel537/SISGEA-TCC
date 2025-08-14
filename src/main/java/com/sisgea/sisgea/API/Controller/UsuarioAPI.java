package com.sisgea.sisgea.API.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.sisgea.BancoDados.Controllers.UsuarioController;
import com.sisgea.Entidades.Usuario;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioAPI {

    @GetMapping("/{id}")
    public Usuario buscarId(@PathVariable String id) {
        Usuario usuario = UsuarioController.buscarUsuario(id);
        if (usuario == null) {
            throw new ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Usuário não encontrado"
            );
        }
        return usuario;
    }

    @GetMapping
    public List<Usuario> listar() {
        return UsuarioController.listarUsuarios();
    }

    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {
        UsuarioController.salvarUsuario(usuario.getUsuario(), usuario.getSenha(), usuario.getPermissao());
        return usuario;
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable String id, @RequestBody Usuario usuario) {
        Usuario existente = UsuarioController.buscarUsuario(id);
        if (existente == null) {
            throw new ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Usuário não encontrado"
            );
        }
        usuario.setUsuario(id);
        UsuarioController.atualizarUsuario(usuario);
        return usuario;
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        Usuario usuario = UsuarioController.buscarUsuario(id);
        if (usuario == null) {
            throw new ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Usuário não encontrado"
            );
        }
        UsuarioController.deletarUsuario(usuario);
    }
}
