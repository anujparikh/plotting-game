package org.unknown.plottingapp.gamengine.rendering;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.io.KeyCommandAdapter;

import javax.swing.JFrame;
import java.awt.Dimension;

public class GraphicsFrame extends JFrame {

    private static final String TITLE = "CoursePlottingGame";
    private static final int FRAME_HEIGHT = 1000;
    private static final int FRAME_WIDTH = 1000;

    public GraphicsFrame(GameState gameState, KeyCommandAdapter adapter, int renderDelay) {
        initFrame(gameState, renderDelay, adapter);
    }

    private void initFrame(GameState gameState, int renderDelay, KeyCommandAdapter adapter) {
        GraphicsPanel graphicsPanel = new GraphicsPanel(FRAME_WIDTH, FRAME_HEIGHT, renderDelay, gameState, adapter);
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        add(graphicsPanel);
        setTitle(TITLE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
