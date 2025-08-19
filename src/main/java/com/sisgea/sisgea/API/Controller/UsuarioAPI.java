package com.sisgea.sisgea.API.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.sisgea.BancoDados.Controllers.UsuarioController;
import com.sisgea.Entidades.Usuario;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioAPI {

    @GetMapping("/{usuariostr}")
    public Usuario buscarId(@PathVariable String usuariostr) {
        Usuario usuario = UsuarioController.buscarUsuario(usuariostr);
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
        UsuarioController.salvarUsuario(usuario);
        return usuario;
    }

    @PutMapping("/{usuariostr}")
    public Usuario atualizar(@PathVariable String usuariostr, @RequestBody Usuario usuario) {
        Usuario existente = UsuarioController.buscarUsuario(usuariostr);
        if (existente == null) {
            throw new ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Usuário não encontrado"
            );
        }
        usuario.setUsuario(usuariostr);
        UsuarioController.atualizarUsuario(usuario);
        return usuario;
    }

    @DeleteMapping("/{usuariostr}")
    public void deletar(@PathVariable String usuariostr) {
        Usuario usuario = UsuarioController.buscarUsuario(usuariostr);
        if (usuario == null) {
            throw new ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Usuário não encontrado"
            );
        }
        UsuarioController.deletarUsuario(usuario);
    }
}
