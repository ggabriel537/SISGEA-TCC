package com.sisgea.BancoDados.Controllers;

import java.util.List;
import java.util.UUID;

import com.sisgea.BancoDados.Models.AlunoModel;
import com.sisgea.Entidades.Aluno;
import com.sisgea.Entidades.Endereco;

public class AlunoController {
    public static void salvarAluno(UUID id, Integer canac, String cpf, String nome, String telefone, String email, String curso, Endereco endereco, Float horas_compradas, Float horas_voadas) {
        Aluno aluno = new Aluno();
        aluno.setId(id);
        aluno.setCanac(canac);
        aluno.setCpf(cpf);
        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
        aluno.setCurso(curso);
        aluno.setEndereco(endereco);
        aluno.setHoras_compradas(horas_compradas);
        aluno.setHoras_voadas(horas_voadas);
        AlunoModel.salvarAluno(aluno);
    }

    public static List<Aluno> listarAlunos() {
        return AlunoModel.listarAlunos();
    }

    public void deletarAluno(Aluno aluno) {
        AlunoModel.excluirAluno(aluno);
    }

    public void atualizarAluno(Aluno aluno) {
        AlunoModel.atualizarAluno(aluno);
    }   
}
