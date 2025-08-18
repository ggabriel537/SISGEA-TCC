package com.sisgea.BancoDados.Models;

import java.util.List;

import com.sisgea.Entidades.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UsuarioModel {
    
    public static void salvarUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(usuario);
        tx.commit();
        em.close();
    }

    public static List<Usuario> listarUsuarios() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        em.close();
        return usuarios;
    }

    public static Usuario buscarUsuario(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        Usuario usuario = em.find(Usuario.class, id);
        em.close();
        return usuario;
    }

    public static void atualizarUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(usuario);
        tx.commit();
        em.close();
    }

    public static void excluirUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(usuario) ? usuario : em.merge(usuario));
        tx.commit();
        em.close();
    }
}
