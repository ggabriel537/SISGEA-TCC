package com.sisgea.BancoDados.Models;

import java.util.List;
import java.util.UUID;

import com.sisgea.Entidades.Discrepancia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DiscrepanciaModel {
    
    public static void salvarDiscrepancia(Discrepancia discrepancia) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(discrepancia);
        tx.commit();
        em.close();
    }

    public static List<Discrepancia> listarDiscrepancias() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Discrepancia> lista = em.createQuery("SELECT d FROM Discrepancia d", Discrepancia.class).getResultList();
        em.close();
        return lista;
    }

    public static Discrepancia buscarDiscrepancia(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        Discrepancia discrepancia = em.find(Discrepancia.class, id);
        em.close();
        return discrepancia;
    }

    public static void atualizarDiscrepancia(Discrepancia discrepancia) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(discrepancia);
        tx.commit();
        em.close();
    }

    public static void excluirDiscrepancia(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        UUID uuid = UUID.fromString(id);
        Discrepancia disc = em.find(Discrepancia.class, uuid);
        if (disc != null) {
            em.remove(disc);
        }
        tx.commit();
        em.close();
    }
}
