package com.sisgea.BancoDados.Models;

import java.util.List;

import com.sisgea.Entidades.Manutencao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ManutencaoModel {
    
    public static void salvarManutencao(Manutencao manutencao) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(manutencao);
        tx.commit();
        em.close();
    }

    public static List<Manutencao> listarManutencoes() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Manutencao> lista = em.createQuery("SELECT m FROM Manutencao m", Manutencao.class).getResultList();
        em.close();
        return lista;
    }

    public static Manutencao buscarId(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        Manutencao manutencao = em.find(Manutencao.class, id);
        em.close();
        return manutencao;
    }

    public static void atualizarManutencao(Manutencao manutencao) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(manutencao);
        tx.commit();
        em.close();
    }

    public static void excluirManutencao(Manutencao manutencao) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(manutencao) ? manutencao : em.merge(manutencao));
        tx.commit();
        em.close();
    }
}
