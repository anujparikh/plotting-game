package org.unknown.plottingapp.gamengine;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.io.CommandAdapter;
import org.unknown.plottingapp.gamengine.logging.LoggerInitializer;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

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
    private final GameRecordingService gameRecordingService;
    private final ExecutorService executorService;
    private static final Logger logger = Logger.getLogger(GameEngine.class.getName());


    public GameEngine() {
        this.renderDelay = 100;
        this.hidPublishDelay = 10;
        this.gameState = new GameState(150, 150, (float) Math.toRadians(0), historyBufferSize);
        this.commandAdapter = new CommandAdapter(this.gameState, TURN_RATE, ACCELERATION);
        this.physicsEngine = new PhysicsEngine(this.gameState, renderDelay, MAP_WIDTH, MAP_HEIGHT);
        this.hidDriver = createHIDDeviceDriver();
        this.gameRecordingService = new GameRecordingService();
        this.executorService = Executors.newFixedThreadPool(3);
    }

    public void start(String sessionName) throws GameAlreadyStartedException {
        gameRecordingService.startSession(sessionName);
        executorService.submit(this.physicsEngine);
        executorService.submit(this.hidDriver);
        executorService.submit(
                gameRecordingService.createGameStateLoggingWorker(this.renderDelay,
                        ()-> GameStateConvertorUtil.convertToGameRecord(this.gameState)));
    }

    public static void main(String[] args) throws GameNotStartedException {
        LoggerInitializer.initLogger();
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
            logger.severe(e.getMessage());
            gameEngine.gameRecordingService.endSession();
            System.exit(0);
        }
    }

    private HIDDriver createHIDDeviceDriver() {
        HIDDriver driver = new HIDDriver(this.hidPublishDelay, this.renderDelay);
        driver.init();
        driver.subscribe(this.commandAdapter::hidCommandHandler);
        return driver;
    }
}
