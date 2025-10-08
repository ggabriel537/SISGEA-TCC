package com.sisgea.sisgea.ControleLogin;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisgea.BancoDados.Controllers.AdministradorController;
import com.sisgea.BancoDados.Controllers.InstrutorController;
import com.sisgea.BancoDados.Controllers.UsuarioController;
import com.sisgea.Entidades.Administrador;
import com.sisgea.Entidades.Instrutor;
import com.sisgea.Entidades.Usuario;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/login")
public class LoginController {

    public static boolean validarLogin(String usuario, String senha) {
        Usuario u = UsuarioController.buscarUsuarioUsername(usuario);
        if (u == null) {
            return false;
        }
        BCryptPasswordEncoder senhahash = new BCryptPasswordEncoder();
        if (!senhahash.matches(senha, u.getSenha())) {
            return false;
        }
        List<Administrador> administradores = AdministradorController.listarAdministradores();
        for (Administrador adm : administradores) {
            if (adm.getUsuario().getUsuario().equals(u.getUsuario()) && adm.getAtivo()) {
                return true;
            }
        }
        List<Instrutor> instrutores = InstrutorController.listarInstrutores();
        for (Instrutor inst : instrutores) {
            if (inst.getUsuario().getUsuario().equals(u.getUsuario()) && inst.getAtivo()) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/logar")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        if (validarLogin(username, password)) {
            JwtUtil jwtUtil = new JwtUtil();
            String token = jwtUtil.generateToken(username);

            Usuario user = UsuarioController.buscarUsuarioUsername(username);
            String nomeUsuario = "Usuário"; // valor padrão caso não encontre

            int perm = user.getPermissao();
            switch (perm) {
                case 1: { // Administrador
                    List<Administrador> admins = AdministradorController.listarAdministradores();
                    for (Administrador admin : admins) {
                        if (admin.getUsuario().getUsuario().equals(user.getUsuario())) {
                            nomeUsuario = admin.getNome();
                            break;
                        }
                    }
                    break;
                }
                case 0: { // Instrutor
                    List<Instrutor> instrutores = InstrutorController.listarInstrutores();
                    for (Instrutor instrutor : instrutores) {
                        if (instrutor.getUsuario().getUsuario().equals(user.getUsuario())) {
                            nomeUsuario = instrutor.getNome();
                            break;
                        }
                    }
                    break;
                }
                default:
                    nomeUsuario = "Usuário";
                    break;
            }
            Map<String, String> resposta = new HashMap<>();
            resposta.put("token", token);
            resposta.put("nome", nomeUsuario);

            return ResponseEntity.ok(resposta);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Usuário ou senha incorretos.");
    }
}
