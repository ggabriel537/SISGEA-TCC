package com.sisgea.BancoDados.Models;

import java.util.List;

import com.sisgea.Entidades.Instrutor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class InstrutorModel {
    
    public static void salvarInstrutor(Instrutor instrutor) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(instrutor);
        tx.commit();
        em.close();
    }

    public static List<Instrutor> listarInstrutores() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Instrutor> lista = em.createQuery("SELECT i FROM Instrutor i", Instrutor.class).getResultList();
        em.close();
        return lista;
    }

    public static void atualizarInstrutor(Instrutor instrutor) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(instrutor);
        tx.commit();
        em.close();
    }

    public static void excluirInstrutor(Instrutor instrutor) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(instrutor) ? instrutor : em.merge(instrutor));
        tx.commit();
        em.close();
    }
}
