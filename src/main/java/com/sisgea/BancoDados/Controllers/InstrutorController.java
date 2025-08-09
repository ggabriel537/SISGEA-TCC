package com.sisgea.BancoDados.Controllers;

import java.util.List;

import com.sisgea.BancoDados.Models.InstrutorModel;
import com.sisgea.Entidades.Endereco;
import com.sisgea.Entidades.Instrutor;

public class InstrutorController {
    public static void salvarInstrutor(
        String nome,
        String usuario,
        String senha,
        Integer permissao,
        Integer canac,
        String cpf,
        String telefone,
        String email,
        String habilitacao,
        Endereco endereco
    ) {
        Instrutor instrutor = new Instrutor();
        instrutor.setNome(nome);
        instrutor.setUsuario(usuario);
        instrutor.setSenha(senha);
        instrutor.setPermissao(permissao);
        instrutor.setCanac(canac);
        instrutor.setCpf(cpf);
        instrutor.setTelefone(telefone);
        instrutor.setEmail(email);
        instrutor.setHabilitacao(habilitacao);
        instrutor.setEndereco(endereco);
        InstrutorModel.salvarInstrutor(instrutor);
    }

    public static List<Instrutor> listarInstrutores() {
        return InstrutorModel.listarInstrutores();
    }

    public void deletarInstrutor(Instrutor instrutor) {
        InstrutorModel.excluirInstrutor(instrutor);
    }

    public void atualizarInstrutor(Instrutor instrutor) {
        InstrutorModel.atualizarInstrutor(instrutor);
    }
}
