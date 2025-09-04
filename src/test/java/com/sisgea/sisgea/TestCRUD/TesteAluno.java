package com.sisgea.sisgea.TestCRUD;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.AlunoController;
import com.sisgea.Entidades.Aluno;
import com.sisgea.Entidades.Endereco;

class TesteAluno {
    @Test
    void testSalvarAluno() {
        Endereco endereco = new Endereco();
        AlunoController.salvarAluno("123.456.789-00", 1234, "Gabriel", "99999-9999", "gabriel@email.com",
                "Engenharia", endereco, 10f, 5f);
        List<Aluno> lista = AlunoController.listarAlunos();
        assertFalse(lista.isEmpty(), "Aluno não foi salvo.");
        Aluno aluno = null;
        for (Aluno a : lista) {
            if ("Gabriel".equals(a.getNome())) {
                aluno = a;
                break;
            }
        }
        assertNotNull(aluno, "Aluno não encontrado.");
    }

    @Test
    void testListarAlunos() {
        List<Aluno> lista = AlunoController.listarAlunos();
        assertNotNull(lista, "Lista null.");
    }

    @Test
    void testAtualizarAluno() {
        Endereco endereco = new Endereco();
        AlunoController.salvarAluno("987.654.321-00", 1235, "Carlos", "99999-8888", "carlos@email.com", "Medicina",
                endereco, 20f, 10f);
        Aluno aluno = null;
        for (Aluno a : AlunoController.listarAlunos()) {
            if ("Carlos".equals(a.getNome())) {
                aluno = a;
                break;
            }
        }
        assertNotNull(aluno, "Aluno para update não encontrado.");
        aluno.setNome("Carlos Atualizado");
        AlunoController.atualizarAluno(aluno);
        Aluno atualizado = null;
        for (Aluno a : AlunoController.listarAlunos()) {
            if ("Carlos Atualizado".equals(a.getNome())) {
                atualizado = a;
                break;
            }
        }
        assertNotNull(atualizado, "Aluno não foi atualizado.");
    }

    @Test
    void testDeletarAluno() {
        Endereco endereco = new Endereco();
        AlunoController.salvarAluno("111.222.333-44", 1236, "Pedro", "99999-7777", "pedro@email.com", "Direito",
                endereco, 15f, 7f);
        Aluno aluno = null;
        for (Aluno a : AlunoController.listarAlunos()) {
            if ("Pedro".equals(a.getNome())) {
                aluno = a;
                break;
            }
        }
        assertNotNull(aluno, "Aluno para deletar não encontrado.");
        AlunoController.deletarAluno(aluno);
        Aluno deletado = null;
        for (Aluno a : AlunoController.listarAlunos()) {
            if ("Pedro".equals(a.getNome())) {
                deletado = a;
                break;
            }
        }
        assertNull(deletado, "Aluno não foi deletado.");
    }
}
