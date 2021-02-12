package org.unknown.plottingengine.gamerecorder.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GameEntityManagerFactory {

    private static EntityManager entityManager;

    private GameEntityManagerFactory() {
    }

    public static EntityManager createManager() {
        if (entityManager == null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CRM");
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }
}
