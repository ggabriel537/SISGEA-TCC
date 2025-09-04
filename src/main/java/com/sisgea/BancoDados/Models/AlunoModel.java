package com.sisgea.BancoDados.Models;

import java.util.List;

import com.sisgea.Entidades.Aluno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AlunoModel {
    
    public static void salvarAluno(Aluno aluno) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(aluno);
        tx.commit();
        em.close();
    }

    public static List<Aluno> listarAlunos() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Aluno> lista = em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
        em.close();
        return lista;
    }

    public static Aluno buscarAluno(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();
        Aluno aluno = em.find(Aluno.class, cpf);
        em.close();
        return aluno;
    }

    public static void atualizarAluno(Aluno aluno) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(aluno);
        tx.commit();
        em.close();
    }

    public static void excluirAluno(Aluno aluno) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(aluno) ? aluno : em.merge(aluno));
        tx.commit();
        em.close();
    }
}
