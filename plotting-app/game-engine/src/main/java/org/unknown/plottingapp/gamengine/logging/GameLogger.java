package org.unknown.plottingapp.gamengine.logging;

import org.unknown.plottingapp.gamengine.datatypes.GameState;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GameLogger {
    private static final LogManager logManager = LogManager.getLogManager();
    private static final Logger logger = Logger.getLogger(GameLogger.class.getName());

    static {
        try {
            logManager.readConfiguration(GameLogger.class.getResourceAsStream("/logger.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while reading configuration file", e);
        }
    }
    public void log(Level logLevel, String message) {
        logger.log(logLevel, message);
    }

    public void log(Level logLevel, GameState gameState) {
        logger.log(logLevel, gameState.toString());
    }
}
