package org.unknown.plottingapp.gamegui;

import javax.swing.JFrame;
import java.awt.Dimension;

public class GraphicsFrame extends JFrame {

    private static final String TITLE = "CoursePlottingGame";
    private static final int FRAME_HEIGHT = 1000;
    private static final int FRAME_WIDTH = 1000;

    public GraphicsFrame() {
        initFrame();
    }

    public static void main(String[] args) {
        GraphicsFrame ex = new GraphicsFrame();
        ex.setVisible(true);
    }

    private void initFrame() {
        GraphicsPanel graphicsPanel = new GraphicsPanel(FRAME_WIDTH, FRAME_HEIGHT);
        setMinimumSize(new Dimension(1000, 1000));
        getContentPane().add(graphicsPanel);
        setTitle(TITLE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
