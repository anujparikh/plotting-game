package org.unknown.plottingengine.gamerecorder.repositories;

import org.unknown.plottingengine.gamerecorder.factory.GameEntityManagerFactory;
import org.unknown.plottingengine.gamerecorder.models.GameRecord;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class GameRecordRepository {

    private final EntityManager entityManager;

    public GameRecordRepository() {
        this.entityManager = GameEntityManagerFactory.createManager();
    }

    public void persist(GameRecord record) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.persist(record);
        transaction.commit();
    }
}
