package org.unknown.plottingapp.gamengine.rendering;

import org.unknown.plottingapp.gamengine.datatypes.GameState;

import javax.swing.JFrame;
import java.awt.Dimension;

public class GraphicsFrame extends JFrame {

    private static final String TITLE = "CoursePlottingGame";
    private static final int FRAME_HEIGHT = 1000;
    private static final int FRAME_WIDTH = 1000;

    public GraphicsFrame(GameState gameState, int renderDelay) {
        initFrame(gameState, renderDelay);
    }

    private void initFrame(GameState gameState, int renderDelay) {
        GraphicsPanel graphicsPanel = new GraphicsPanel(FRAME_WIDTH, FRAME_HEIGHT, renderDelay, gameState);
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        add(graphicsPanel);
        setTitle(TITLE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
