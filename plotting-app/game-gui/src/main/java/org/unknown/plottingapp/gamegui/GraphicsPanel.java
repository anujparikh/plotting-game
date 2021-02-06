package org.unknown.plottingapp.gamegui;


import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GraphicsPanel extends JPanel implements ActionListener {


    private Image shipImage;

    public GraphicsPanel(int frameWidth, int frameHeight) {
        initPanel(frameWidth, frameHeight);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawPanel(graphics);
    }

    private void drawPanel(Graphics graphics) {
        Graphics2D g2d = (Graphics2D)graphics;
        int x_center = 100 + (this.shipImage.getWidth(this) / 2);
        int y_center = 100 + (this.shipImage.getHeight(this) / 2);
        g2d.rotate(Math.toRadians(45), x_center, y_center);
        g2d.drawImage(this.shipImage, 100, 100, this);
        g2d.rotate(Math.toRadians(-45), x_center, y_center);
        g2d.drawImage(this.shipImage, 100, 200, this);

    }

    private void initPanel(int frameWidth, int frameHeight) {

        setMinimumSize(new Dimension(frameWidth, frameHeight));
        setLayout(null);
        setBackground(Color.black);

        loadImage();
    }

    private void loadImage() {
        URL resource = this.getClass().getResource("/assets/ship.png");
        this.shipImage = new ImageIcon(resource.getPath()).getImage();
    }


}
