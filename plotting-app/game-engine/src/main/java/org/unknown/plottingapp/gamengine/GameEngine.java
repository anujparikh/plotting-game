package org.unknown.plottingapp.gamengine;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.io.CommandAdapter;
import org.unknown.plottingapp.gamengine.logging.GameLogger;
import org.unknown.plottingapp.gamengine.physics.PhysicsEngine;
import org.unknown.plottingapp.gamengine.ui.GraphicsFrame;
import org.unknown.plottingapp.gamengine.utils.GameStateConvertorUtil;
import org.unknown.plottingapp.hiddriver.driver.HIDDriver;
import org.unknown.plottingengine.gamerecorder.services.GameRecordingService;
import org.unknown.plottingengine.gamerecorder.exceptions.GameAlreadyStartedException;
import org.unknown.plottingengine.gamerecorder.exceptions.GameNotStartedException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Callable;
import java.util.logging.Level;

public class GameEngine {
    private static final String titlePrefix = "Course Plotter v0.1";
    private static final float TURN_RATE = 5;
    private static final float ACCELERATION = 10;
    private static final int MAP_WIDTH = 1000;
    private static final int MAP_HEIGHT = 1000;
    private final int historyBufferSize = 1000; // Could be increased
    private final int renderDelay;
    private final int hidPublishDelay;
    private final GameState gameState;
    private final CommandAdapter commandAdapter;
    private final PhysicsEngine physicsEngine;
    private final HIDDriver hidDriver;
    private final GameLogger gameLogger;
    private final GameRecordingService gameRecordingService;

    public GameEngine() {
        this.renderDelay = 100;
        this.hidPublishDelay = 10;
        this.gameState = new GameState(150, 150, (float) Math.toRadians(0), historyBufferSize);
        this.commandAdapter = new CommandAdapter(this.gameState, TURN_RATE, ACCELERATION);
        this.physicsEngine = new PhysicsEngine(this.gameState, renderDelay, MAP_WIDTH, MAP_HEIGHT);
        this.hidDriver = createHIDDeviceDriver();
        this.gameLogger = new GameLogger();
        this.gameRecordingService = new GameRecordingService();
    }

    public void start(String sessionName) throws GameAlreadyStartedException {
        Thread engineThread = new Thread(this.physicsEngine);
        Thread hidDriverThread = new Thread(this.hidDriver);
        Thread gameStateLoggingThread = new Thread(createGameStateLoggingWorker());
        gameRecordingService.startSession(sessionName);
        engineThread.start();
        hidDriverThread.start();
        gameStateLoggingThread.start();
    }

    public static void main(String[] args) throws GameNotStartedException {
        GameEngine gameEngine = new GameEngine();
        String title = titlePrefix + "_" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        Callable<Void> onDisposeHandler = () -> {
            gameEngine.gameRecordingService.endSession();
            return null;
        };
        try {
            GraphicsFrame mainFrame = new GraphicsFrame(gameEngine.gameState, gameEngine.commandAdapter,
                    gameEngine.renderDelay, title, false, onDisposeHandler);
            GraphicsFrame historyFrame = new GraphicsFrame(gameEngine.gameState, gameEngine.commandAdapter,
                    gameEngine.renderDelay, title + "_history", true, onDisposeHandler);
            mainFrame.setVisible(true);
            historyFrame.setVisible(true);
            gameEngine.start(title);
        } catch (Exception e) {
            gameEngine.gameLogger.log(Level.SEVERE, e.getMessage());
            gameEngine.gameRecordingService.endSession();
        }
    }

    private HIDDriver createHIDDeviceDriver() {
        HIDDriver driver = new HIDDriver(this.hidPublishDelay, this.renderDelay);
        driver.init();
        driver.subscribe(this.commandAdapter::hidCommandHandler);
        return driver;
    }

    private Runnable createGameStateLoggingWorker() {
        return () -> {
            while (true) {
                try {
                    gameLogger.log(Level.INFO, gameState);
                    gameRecordingService.addRecord(GameStateConvertorUtil.convertToGameRecord(this.gameState));
                    Thread.sleep(renderDelay);
                } catch (InterruptedException | GameNotStartedException e) {
                    gameLogger.log(Level.SEVERE, e.getMessage());
                }
            }
        };
    }
}
