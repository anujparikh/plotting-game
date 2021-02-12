package org.unknown.plottingengine.gamerecorder.services;

import org.unknown.plottingengine.gamerecorder.exceptions.GameAlreadyStartedException;
import org.unknown.plottingengine.gamerecorder.exceptions.GameNotStartedException;
import org.unknown.plottingengine.gamerecorder.models.GameRecord;
import org.unknown.plottingengine.gamerecorder.models.GameSession;
import org.unknown.plottingengine.gamerecorder.repositories.GameRecordRepository;
import org.unknown.plottingengine.gamerecorder.repositories.GameSessionRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class GameRecordingService {
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
}
