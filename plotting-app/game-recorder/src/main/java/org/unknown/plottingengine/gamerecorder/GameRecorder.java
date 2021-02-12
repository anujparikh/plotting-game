package org.unknown.plottingengine.gamerecorder;

import org.unknown.plottingengine.gamerecorder.exceptions.GameAlreadyStartedException;
import org.unknown.plottingengine.gamerecorder.exceptions.GameNotStartedException;
import org.unknown.plottingengine.gamerecorder.models.GameSession;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class GameRecorder {
    private static GameRecorder gameRecorderInstance;
    private final EntityManager entityManager;
    private GameSession gameSession;

    private GameRecorder() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CRM");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void startSession(String sessionName) throws GameAlreadyStartedException {
        if (gameSession == null) { // Session can only be started once
            gameSession = new GameSession();
            gameSession.setTitle(sessionName);
            EntityTransaction transaction = this.entityManager.getTransaction();
            transaction.begin();
            this.entityManager.persist(gameSession);
            transaction.commit();
        } else {
            throw new GameAlreadyStartedException();
        }
    }

    public void endSession() throws GameNotStartedException {
        if (gameSession != null) {
            EntityTransaction transaction = this.entityManager.getTransaction();
            transaction.begin();
            gameSession.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
            transaction.commit();
        } else {
            throw new GameNotStartedException();
        }
    }

    public static GameRecorder getInstance() {
        if (gameRecorderInstance == null) {
            gameRecorderInstance = new GameRecorder();
        }
        return gameRecorderInstance;
    }

}
