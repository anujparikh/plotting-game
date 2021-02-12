package org.unknown.plottingengine.gamerecorder;

import org.unknown.plottingengine.gamerecorder.exceptions.GameAlreadyStartedException;
import org.unknown.plottingengine.gamerecorder.exceptions.GameNotStartedException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    private EntityManager entityManager;

    public Main() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CRM");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public static void main(String[] args) throws InterruptedException, GameAlreadyStartedException, GameNotStartedException {
        GameRecorder gameRecorder = GameRecorder.getInstance();
        gameRecorder.startSession("MySession");
        System.out.println("session started");
        Thread.sleep(5000);
        System.out.println("session ended");
        gameRecorder.endSession();
    }
}
