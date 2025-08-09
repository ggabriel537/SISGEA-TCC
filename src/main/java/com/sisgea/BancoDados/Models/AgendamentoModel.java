package com.sisgea.BancoDados.Models;

import java.util.List;

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

    public static void atualizarAgendamento(Agendamento agendamento) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(agendamento);
        tx.commit();
        em.close();
    }

    public static void excluirAgendamento(Agendamento agendamento) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(agendamento) ? agendamento : em.merge(agendamento));
        tx.commit();
        em.close();
    }
}
