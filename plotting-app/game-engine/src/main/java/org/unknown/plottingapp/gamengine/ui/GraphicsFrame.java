package org.unknown.plottingapp.gamengine.ui;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.io.CommandAdapter;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class GraphicsFrame extends JFrame {

    private static final int FRAME_HEIGHT = 1000;
    private static final int FRAME_WIDTH = 1000;
    private static final Logger logger = Logger.getLogger(GraphicsFrame.class.getName());
    private final Callable<Void> onDisposeHandler;

    public GraphicsFrame(GameState gameState, CommandAdapter adapter, int renderDelay,
                         String frameTitle, boolean isHistoryPlotEnabled, Callable<Void> onDisposeHandler) {
        this.onDisposeHandler = onDisposeHandler;
        initFrame(gameState, renderDelay, adapter, frameTitle, isHistoryPlotEnabled);
    }

    @Override
    public void dispose() {
        super.dispose();
        try {
            onDisposeHandler.call();
            System.exit(0);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    private void initFrame(GameState gameState, int renderDelay, CommandAdapter adapter,
                           String frameTitle, boolean isHistoryPlotEnabled) {
        GraphicsPanel graphicsPanel = new GraphicsPanel(FRAME_WIDTH, FRAME_HEIGHT, renderDelay,
                gameState, adapter, isHistoryPlotEnabled);
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        add(graphicsPanel);
        setTitle(frameTitle);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
