package com.sisgea.BancoDados.Controllers;

import java.util.List;

import com.sisgea.BancoDados.Models.AlunoModel;
import com.sisgea.Entidades.Aluno;
import com.sisgea.Entidades.Endereco;

public class AlunoController {
    public static void salvarAluno(String cpf, Integer canac, String nome, String telefone, String email, String curso, Endereco endereco, Float horas_compradas, Float horas_voadas) {
        Aluno aluno = new Aluno();
        aluno.setCpf(cpf);
        aluno.setCanac(canac);
        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
        aluno.setCurso(curso);
        aluno.setEndereco(endereco);
        aluno.setHoras_compradas(horas_compradas);
        aluno.setHoras_voadas(horas_voadas);
        AlunoModel.salvarAluno(aluno);
    }

    public static void salvarAluno(Aluno aluno) {
        AlunoModel.salvarAluno(aluno);
    }

    public static List<Aluno> listarAlunos() {
        return AlunoModel.listarAlunos();
    }

    public static Aluno buscarId(String id) {
        return AlunoModel.buscarAluno(id);
    }

    public static void deletarAluno(Aluno aluno) {
        AlunoModel.excluirAluno(aluno);
    }

    public static void atualizarAluno(Aluno aluno) {
        AlunoModel.atualizarAluno(aluno);
    }   
}
