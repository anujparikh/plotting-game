package org.unknown.plottingapp.gamengine.rendering;


import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.io.KeyCommandAdapter;

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
    private final KeyCommandAdapter adapter;
    private Image shipImage;

    public GraphicsPanel(int frameWidth, int frameHeight, int delay, GameState gameState, KeyCommandAdapter adapter) {
        this.gameState = gameState;
        this.adapter = adapter;
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
        int x_center = this.gameState.getX() + (this.shipImage.getWidth(this) / 2);
        int y_center = this.gameState.getY() + (this.shipImage.getHeight(this) / 2);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(Math.toRadians(this.gameState.getTheta()), x_center, y_center);
        g2d.drawImage(this.shipImage, this.gameState.getX(), this.gameState.getY(), this);
        g2d.rotate(Math.toRadians(-this.gameState.getTheta()), x_center, y_center);
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
