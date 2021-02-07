package org.unknown.plottingapp.gamengine;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.io.CommandAdapter;
import org.unknown.plottingapp.gamengine.physics.PhysicsEngine;
import org.unknown.plottingapp.gamengine.rendering.GraphicsFrame;
import org.unknown.plottingapp.hiddriver.driver.HIDDriver;

public class GameEngine {
    private static final String title = "Course Plotter v0.1";
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

    public GameEngine() {
        this.renderDelay = 100;
        this.hidPublishDelay = 10;
        this.gameState = new GameState(150, 150, (float) Math.toRadians(0), historyBufferSize);
        this.commandAdapter = new CommandAdapter(this.gameState, TURN_RATE, ACCELERATION);
        this.physicsEngine = new PhysicsEngine(this.gameState, renderDelay, MAP_WIDTH, MAP_HEIGHT);
        this.hidDriver = createHIDDeviceDriver();
    }

    public void start() {
        Thread engineThread = new Thread(this.physicsEngine);
        Thread hidDriverThread = new Thread(this.hidDriver);
        engineThread.start();
        hidDriverThread.start();
    }

    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        GraphicsFrame mainFrame = new GraphicsFrame(gameEngine.gameState, gameEngine.commandAdapter,
                gameEngine.renderDelay, title, false);
        GraphicsFrame historyFrame = new GraphicsFrame(gameEngine.gameState, gameEngine.commandAdapter,
                gameEngine.renderDelay, title + "_history", true);
        mainFrame.setVisible(true);
        historyFrame.setVisible(true);
        gameEngine.start();
    }

    private HIDDriver createHIDDeviceDriver() {
        HIDDriver driver = new HIDDriver(this.hidPublishDelay, this.renderDelay);
        driver.init();
        driver.subscribe(this.commandAdapter::hidCommandHandler);
        return driver;
    }
}
