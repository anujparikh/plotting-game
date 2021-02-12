package org.unknown.plottingengine.gamerecorder.repositories;

import org.unknown.plottingengine.gamerecorder.factory.GameEntityManagerFactory;
import org.unknown.plottingengine.gamerecorder.models.GameSession;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


public class GameSessionRepository {
    private final EntityManager entityManager;

    public GameSessionRepository() {
        this.entityManager = GameEntityManagerFactory.createManager();
    }

    public void persist(GameSession gameSession) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.persist(gameSession);
        transaction.commit();
    }
}
