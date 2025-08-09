package com.sisgea.BancoDados.Models;

import java.util.List;

import com.sisgea.Entidades.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EnderecoModel {
    
    public static void salvarEndereco(Endereco endereco) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(endereco);
        tx.commit();
        em.close();
    }

    public static List<Endereco> listarEnderecos() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Endereco> lista = em.createQuery("SELECT e FROM Endereco e", Endereco.class).getResultList();
        em.close();
        return lista;
    }

    public static void atualizarEndereco(Endereco endereco) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(endereco);
        tx.commit();
        em.close();
    }

    public static void excluirEndereco(Endereco endereco) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(endereco) ? endereco : em.merge(endereco));
        tx.commit();
        em.close();
    }
}
