package org.unknown.plottingapp.gamengine;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.io.KeyCommandAdapter;
import org.unknown.plottingapp.gamengine.physics.PhysicsEngine;
import org.unknown.plottingapp.gamengine.rendering.GraphicsFrame;

public class GameEngine {
    public static final float TURN_RATE = 5;
    public static final float ACCELERATION = 10;
    public static final int MAP_WIDTH = 1000;
    public static final int MAP_HEIGHT = 1000;
    private final int renderDelay;
    private final GameState gameState;
    private final KeyCommandAdapter adapter;
    private final PhysicsEngine physicsEngine;

    public GameEngine() {
        this.renderDelay = 100;
        this.gameState = new GameState(150, 150, (float) Math.toRadians(0));
        this.adapter = new KeyCommandAdapter(this.gameState, TURN_RATE, ACCELERATION);
        this.physicsEngine = new PhysicsEngine(this.gameState, renderDelay, MAP_WIDTH, MAP_HEIGHT);
    }

    public void start() {
        Thread engineThread = new Thread(this.physicsEngine);
        engineThread.start();
    }

    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        GraphicsFrame ex = new GraphicsFrame(gameEngine.gameState, gameEngine.adapter, gameEngine.renderDelay);
        ex.setVisible(true);
        gameEngine.start();
    }
}
