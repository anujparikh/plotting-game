package org.unknown.plottingapp.gamengine;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.io.KeyCommandAdapter;
import org.unknown.plottingapp.gamengine.physics.PhysicsEngine;
import org.unknown.plottingapp.gamengine.rendering.GraphicsFrame;

public class GameEngine {
    private final int renderDelay;
    private final GameState gameState;
    private final KeyCommandAdapter adapter;
    private final PhysicsEngine physicsEngine;

    public GameEngine() {
        this.renderDelay = 100;
        this.gameState = new GameState(150, 150, Math.toRadians(0));
        this.adapter = new KeyCommandAdapter(this.gameState);
        this.physicsEngine = new PhysicsEngine(this.gameState, renderDelay, 1000, 1000);
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
