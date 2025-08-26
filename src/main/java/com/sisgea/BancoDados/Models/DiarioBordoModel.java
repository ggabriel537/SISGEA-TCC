package com.sisgea.BancoDados.Models;

import java.util.List;
import java.util.UUID;

import com.sisgea.Entidades.DiarioBordo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DiarioBordoModel {
    
    public static void salvarDiarioBordo(DiarioBordo diario) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(diario);
        tx.commit();
        em.close();
    }

    public static List<DiarioBordo> listarDiariosBordo() {
        EntityManager em = JPAUtil.getEntityManager();
        List<DiarioBordo> lista = em.createQuery("SELECT d FROM DiarioBordo d", DiarioBordo.class).getResultList();
        em.close();
        return lista;
    }

    public static DiarioBordo buscarDiarioBordo(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        UUID uuid = UUID.fromString(id);
        DiarioBordo diario = em.find(DiarioBordo.class, uuid);
        em.close();
        return diario;
    }

    public static void atualizarDiarioBordo(DiarioBordo diario) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(diario);
        tx.commit();
        em.close();
    }

    public static void excluirDiarioBordo(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        UUID uuid = UUID.fromString(id);
        DiarioBordo diario = em.find(DiarioBordo.class, uuid);
        if (diario != null) {
            em.remove(diario);
        }
        tx.commit();
        em.close();
    }
}
