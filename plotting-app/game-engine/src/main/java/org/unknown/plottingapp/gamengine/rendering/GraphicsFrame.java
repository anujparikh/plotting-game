package org.unknown.plottingapp.gamengine.rendering;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.io.CommandAdapter;

import javax.swing.JFrame;
import java.awt.Dimension;

public class GraphicsFrame extends JFrame {

    private static final int FRAME_HEIGHT = 1000;
    private static final int FRAME_WIDTH = 1000;

    public GraphicsFrame(GameState gameState, CommandAdapter adapter, int renderDelay,
                         String frameTitle, boolean isHistoryPlotEnabled) {
        initFrame(gameState, renderDelay, adapter, frameTitle, isHistoryPlotEnabled);
    }

    private void initFrame(GameState gameState, int renderDelay, CommandAdapter adapter,
                           String frameTitle, boolean isHistoryPlotEnabled) {
        GraphicsPanel graphicsPanel = new GraphicsPanel(FRAME_WIDTH, FRAME_HEIGHT, renderDelay,
                gameState, adapter, isHistoryPlotEnabled);
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        add(graphicsPanel);
        setTitle(frameTitle);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
