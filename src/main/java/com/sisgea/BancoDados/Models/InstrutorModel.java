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
        em.merge(instrutor);
        tx.commit();
        em.close();
    }

    public static List<Instrutor> listarInstrutores() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Instrutor> lista = em.createQuery("SELECT i FROM Instrutor i", Instrutor.class).getResultList();
        em.close();
        return lista;
    }

    public static Instrutor buscarInstrutor(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        Instrutor instrutor = em.find(Instrutor.class, id);
        em.close();
        return instrutor;
    }

    public static void atualizarInstrutor(Instrutor instrutor) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(instrutor);
        tx.commit();
        em.close();
    }

    public static void excluirInstrutor(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Instrutor inst = em.find(Instrutor.class, id);
        if (inst != null) {
            em.remove(inst);
        }
        tx.commit();
        em.close();
    }
}
