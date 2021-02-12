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

    private void drawPanel(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        int x_center = Math.round(this.gameState.getCurrentPosition().x)
                + (this.shipImage.getWidth(this) / 2);
        int y_center = Math.round(this.gameState.getCurrentPosition().y)
                + (this.shipImage.getHeight(this) / 2);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(this.gameState.getDirection(), x_center, y_center);
        g2d.drawImage(this.shipImage, Math.round(this.gameState.getCurrentPosition().x),
                Math.round(this.gameState.getCurrentPosition().y), this);
        g2d.rotate(-this.gameState.getDirection(), x_center, y_center);
        g2d.setColor(Color.orange);
        if (this.isHistoryPlotEnabled) {
            this.gameState.getPositionHistory().forEach(
                    position -> g2d.drawOval(
                            Math.round(position.x) + (this.shipImage.getWidth(this) / 2),
                            Math.round(position.y) + (this.shipImage.getHeight(this) / 2),
                            5, 5));
        }
        g2d.dispose();
    }

    private void initPanel(int frameWidth, int frameHeight, Timer timer) {
        addKeyListener(this.adapter);
        setMinimumSize(new Dimension(frameWidth, frameHeight));
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
