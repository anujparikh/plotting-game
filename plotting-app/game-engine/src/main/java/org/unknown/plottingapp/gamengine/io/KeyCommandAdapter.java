package org.unknown.plottingapp.gamengine.io;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.gamengine.rendering.GraphicsPanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyCommandAdapter extends KeyAdapter {

    private final GameState gameState;
    private double turnRate;
    private double acceleration;

    public KeyCommandAdapter(GameState gameState, double turnRate, double acceleration) {
        this.gameState = gameState;
        this.turnRate = turnRate;
        this.acceleration = acceleration;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        switch (key) {
            case 37:
                gameState.setTheta(gameState.getTheta() - Math.toRadians(turnRate));
                break;
            case 39:
                gameState.setTheta(gameState.getTheta() + Math.toRadians(turnRate));
                break;
            case 107:
                gameState.setVelocity(gameState.getVelocity() + acceleration);
                break;
            case 109:
                gameState.setVelocity(gameState.getVelocity() - acceleration);
                break;
        }
    }
}
