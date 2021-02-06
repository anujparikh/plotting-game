package org.unknown.plottingapp.gamegui;


import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
        graphics.drawImage(this.shipImage, 100, 100, this);
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
