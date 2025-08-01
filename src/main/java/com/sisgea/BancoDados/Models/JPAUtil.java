package com.sisgea.BancoDados.Models;

import jakarta.persistence.*;

public class JPAUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BancoDados");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }
}
