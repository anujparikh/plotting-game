package org.unknown.plottingapp.gamengine;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.io.KeyCommandAdapter;
import org.unknown.plottingapp.gamengine.rendering.GraphicsFrame;

public class GameEngine {
    private final GameState gameState;
    private final KeyCommandAdapter adapter;

    public GameEngine() {
        this.gameState = new GameState(150, 150, 45);
        this.adapter = new KeyCommandAdapter(this.gameState);
    }

    public static void main(String[] args) {
        int renderDelay = 10;
        GameEngine gameEngine = new GameEngine();

        GraphicsFrame ex = new GraphicsFrame(gameEngine.gameState, gameEngine.adapter, renderDelay);
        ex.setVisible(true);
    }
}
