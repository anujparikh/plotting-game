package org.unknown.plottingapp.gamedatalogging;

import org.unknown.plottingapp.gamedatalogging.datatypes.GameLog;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GameLogger {
    private static final LogManager logManager = LogManager.getLogManager();
    private static final Logger eventLogger = Logger.getLogger(GameLogger.class.getName());

    static {
        try {
            logManager.readConfiguration(GameLogger.class.getResourceAsStream("/logger.properties"));
        } catch (IOException e) {
            eventLogger.log(Level.SEVERE, "Error while reading configuration file", e);
        }
    }

    public static void log(Level logLevel, GameLog gameLog) {
        eventLogger.log(logLevel, gameLog.toString());
    }
}
