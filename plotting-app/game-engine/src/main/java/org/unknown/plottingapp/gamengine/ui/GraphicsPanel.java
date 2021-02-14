package org.unknown.plottingapp.gamengine.ui;


import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.io.CommandAdapter;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GraphicsPanel extends JPanel implements ActionListener {

    private static final int GRID_SPACING = 10;
    private final GameState gameState;
    private final CommandAdapter adapter;
    private final boolean isHistoryPlotEnabled;
    private Image shipImage;

    public GraphicsPanel(int frameWidth, int frameHeight, int delay, GameState gameState, CommandAdapter adapter,
                         boolean isHistoryPlotEnabled) {
        this.gameState = gameState;
        this.adapter = adapter;
        this.isHistoryPlotEnabled = isHistoryPlotEnabled;
        Timer timer = new Timer(delay, this);
        initPanel(frameWidth, frameHeight, timer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawPanel(graphics);
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawCourseHistory(Graphics2D graphics2D) {
        graphics2D.setColor(Color.orange);
        this.gameState.getPositionHistory().forEach(
                position -> graphics2D.drawOval(
                        Math.round(position.x) + (this.shipImage.getWidth(this) / 2),
                        Math.round(position.y) + (this.shipImage.getHeight(this) / 2),
                        5, 5));
    }

    private void drawBoat(Graphics2D graphics2D) {
        int x_center = Math.round(this.gameState.getCurrentPosition().x)
                + (this.shipImage.getWidth(this) / 2);
        int y_center = Math.round(this.gameState.getCurrentPosition().y)
                + (this.shipImage.getHeight(this) / 2);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.rotate(this.gameState.getDirection(), x_center, y_center);
        graphics2D.drawImage(this.shipImage, Math.round(this.gameState.getCurrentPosition().x),
                Math.round(this.gameState.getCurrentPosition().y), this);
        graphics2D.rotate(-this.gameState.getDirection(), x_center, y_center);
    }

    private void drawGrid(Graphics2D graphics2D) {
        // draw vertical
        for (int i = GRID_SPACING; i <  this.getWidth(); i+= GRID_SPACING) {
            graphics2D.drawLine(i, 0, i, this.getHeight());
        }
        // draw horizontal
        for (int i = GRID_SPACING; i <  this.getWidth(); i+= GRID_SPACING) {
            graphics2D.drawLine(0, i, this.getWidth(), i);
        }
    }

    private void drawPanel(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        drawBoat(g2d);
        drawGrid(g2d);
        if (this.isHistoryPlotEnabled) {
            drawCourseHistory(g2d);
        }
        g2d.dispose();
    }

    private void initPanel(int frameWidth, int frameHeight, Timer timer) {
        Dimension frameDimension = new Dimension(frameWidth, frameHeight);
        addKeyListener(this.adapter);
        setMinimumSize(frameDimension);
        setSize(frameDimension);
        setLayout(null);
        setBackground(Color.black);
        setFocusable(true);
        loadImage();
        timer.start();
    }

    private void loadImage() {
        URL resource = this.getClass().getResource("/assets/ship.png");
        this.shipImage = new ImageIcon(resource.getPath()).getImage();
    }

}
