package org.unknown.plottingapp.gamengine.logging;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerInitializer {
    private static final LogManager logManager = LogManager.getLogManager();
    private static final Logger logger = Logger.getLogger(LoggerInitializer.class.getName());

    public static void initLogger() {
        try {
            logManager.readConfiguration(LoggerInitializer.class.getResourceAsStream("/logger.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while reading configuration file", e);
        }
    }
}
