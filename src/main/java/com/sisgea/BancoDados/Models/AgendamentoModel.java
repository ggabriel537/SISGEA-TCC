package com.sisgea.BancoDados.Models;

import java.util.List;
import java.util.UUID;

import com.sisgea.Entidades.Agendamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AgendamentoModel {

    public static void salvarAgendamento(Agendamento agendamento) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(agendamento);
        tx.commit();
        em.close();
    }

    public static List<Agendamento> listarAgendamentos() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Agendamento> lista = em.createQuery("SELECT a FROM Agendamento a", Agendamento.class).getResultList();
        em.close();
        return lista;
    }

    public static Agendamento buscarAgendamento(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        UUID uuid = UUID.fromString(id);
        Agendamento agendamento = em.find(Agendamento.class, uuid);
        em.close();
        return agendamento;
    }

    public static void atualizarAgendamento(Agendamento agendamento) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(agendamento);
        tx.commit();
        em.close();
    }

    public static void excluirAgendamento(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        UUID uuid = UUID.fromString(id);
        Agendamento agendamento = em.find(Agendamento.class, uuid);
        if (agendamento != null) {
            em.remove(agendamento);
        }
        tx.commit();
        em.close();
    }
}
