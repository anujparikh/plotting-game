package org.unknown.plottingapp.gamengine;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.rendering.GraphicsFrame;

public class GameEngine {
    public static void main(String[] args) {
        GameState gameState = new GameState(150, 150, 45);
        int renderDelay = 10;
        GraphicsFrame ex = new GraphicsFrame(gameState, renderDelay);
        ex.setVisible(true);
    }
}
