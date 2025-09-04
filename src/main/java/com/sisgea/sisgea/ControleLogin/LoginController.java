package com.sisgea.sisgea.ControleLogin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisgea.BancoDados.Controllers.UsuarioController;
import com.sisgea.Entidades.Usuario;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/login")
public class LoginController {
    public static boolean validarLogin(String usuario, String senha) {
        Usuario u = UsuarioController.buscarUsuario(usuario);
        if (u != null) {
            BCryptPasswordEncoder senhahash = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
            if (senhahash.matches(senha, u.getSenha())) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/logar")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        if (validarLogin(username, password)) {
            JwtUtil jwtUtil = new JwtUtil();
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário ou senha incorretos.");
        }
    }

}
