package com.sisgea.BancoDados.Models;

import java.util.List;

import com.sisgea.Entidades.Administrador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AdministradorModel {
    
    public static void salvarAdministrador(Administrador administrador) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(administrador);
        tx.commit();
        em.close();
    }

    public static List<Administrador> listarAdministradores() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Administrador> lista = em.createQuery("SELECT a FROM Administrador a", Administrador.class).getResultList();
        em.close();
        return lista;
    }

    public static Administrador buscarAdministrador(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        Administrador administrador = em.find(Administrador.class, id);
        em.close();
        return administrador;
    }

    public static void atualizarAdministrador(Administrador administrador) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(administrador);
        tx.commit();
        em.close();
    }

    public static void excluirAdministrador(Administrador administrador) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(administrador) ? administrador : em.merge(administrador));
        tx.commit();
        em.close();
    }
}
