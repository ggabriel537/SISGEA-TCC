package com.sisgea.BancoDados.Models;

import java.util.List;

import com.sisgea.Entidades.Aeronave;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AeronaveModel {
    
    public static void salvarAeronave(Aeronave aeronave) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(aeronave);
        tx.commit();
        em.close();
    }

    public static List<Aeronave> listarAeronaves() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Aeronave> lista = em.createQuery("SELECT a FROM Aeronave a", Aeronave.class).getResultList();
        em.close();
        return lista;
    }

    public static void atualizarAeronave(Aeronave aeronave) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(aeronave);
        tx.commit();
        em.close();
    }

    public static void excluirAeronave(Aeronave aeronave) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(aeronave) ? aeronave : em.merge(aeronave));
        tx.commit();
        em.close();
    }
}
