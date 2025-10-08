package com.sisgea.BancoDados.Controllers;

import java.util.List;

import com.sisgea.BancoDados.Models.InstrutorModel;
import com.sisgea.Entidades.Instrutor;
import com.sisgea.Entidades.Usuario;

public class InstrutorController {

    public static void salvarInstrutor(String nome, String cpf, String telefone, String email,
                                       String usuarioStr, String senha, Integer permissao) {
        Usuario usuario = new Usuario(usuarioStr, senha, permissao);
        Instrutor instrutor = new Instrutor(usuario, nome, cpf, telefone, email, null, null);
        instrutor.setAtivo(true);
        InstrutorModel.salvarInstrutor(instrutor);
    }

    public static void salvarInstrutor(Instrutor instrutor) {
        instrutor.setAtivo(true);
        InstrutorModel.salvarInstrutor(instrutor);
    }

    public static List<Instrutor> listarInstrutores() {
        return InstrutorModel.listarInstrutores();
    }

    public static List<Instrutor> listarTodosInstrutores() {
        return InstrutorModel.listarTodosInstrutores();
    }

    public static Instrutor buscarId(String id) {
        return InstrutorModel.buscarInstrutor(id);
    }

    public static void deletarInstrutor(String id) {
        Instrutor instrutor = InstrutorModel.buscarInstrutor(id);
        if (instrutor != null) {
            instrutor.setAtivo(false);
            InstrutorModel.atualizarInstrutor(instrutor);
        }
    }

    public static void atualizarInstrutor(Instrutor instrutor) {
        InstrutorModel.atualizarInstrutor(instrutor);
    }
}
