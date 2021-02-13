package org.unknown.plottingengine.gamerecorder.services;

import org.unknown.plottingengine.gamerecorder.exceptions.GameAlreadyStartedException;
import org.unknown.plottingengine.gamerecorder.exceptions.GameNotStartedException;
import org.unknown.plottingengine.gamerecorder.models.GameRecord;
import org.unknown.plottingengine.gamerecorder.models.GameSession;
import org.unknown.plottingengine.gamerecorder.repositories.GameRecordRepository;
import org.unknown.plottingengine.gamerecorder.repositories.GameSessionRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class GameRecordingService {
    private static final Logger logger = Logger.getLogger(GameRecordingService.class.getName());
    private final GameRecordRepository gameRecordRepository;
    private final GameSessionRepository gameSessionRepository;
    private GameSession gameSession;

    public GameRecordingService() {
        this.gameRecordRepository = new GameRecordRepository();
        this.gameSessionRepository = new GameSessionRepository();
    }

    public void startSession(String sessionName) throws GameAlreadyStartedException {
        if (gameSession == null) { // Session can only be started once
            gameSession = new GameSession();
            gameSession.setTitle(sessionName);
            gameSessionRepository.persist(gameSession);
        } else {
            throw new GameAlreadyStartedException();
        }
    }

    public void endSession() throws GameNotStartedException {
        if (gameSession != null) {
            gameSession.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
            gameSessionRepository.persist(gameSession);
            gameSession = null;
        } else {
            throw new GameNotStartedException();
        }
    }

    public void addRecord(GameRecord record) throws GameNotStartedException {
        if (gameSession != null) {
            record.setGameSession(gameSession);
            gameRecordRepository.persist(record);
        } else {
            throw new GameNotStartedException();
        }
    }

    public Runnable createGameStateLoggingWorker(int renderDelay, Supplier<GameRecord> gameRecordSupplier) {
        return () -> {
            while (true) {
                try {
                    GameRecord gameRecord = gameRecordSupplier.get();
                    logger.info(gameRecord.toString());
                    addRecord(gameRecord);
                    Thread.sleep(renderDelay);
                } catch (InterruptedException | GameNotStartedException e) {
                    logger.severe(e.getMessage());
                }
            }
        };
    }
}
