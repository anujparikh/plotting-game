package org.unknown.plottingapp.gamengine.io;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingapp.hiddriver.datatypes.HIDState;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CommandAdapter extends KeyAdapter {

    private final GameState gameState;
    private final float turnRate;
    private final float acceleration;

    public CommandAdapter(GameState gameState, float turnRate, float acceleration) {
        this.gameState = gameState;
        this.turnRate = turnRate;
        this.acceleration = acceleration;
    }

    public void hidCommandHandler(HIDState hidState) {
        switch (hidState.getRightLR()) {
            case LEFT:
                gameState.setDirection(gameState.getDirection() - (float) Math.toRadians(turnRate));
                break;
            case RIGHT:
                gameState.setDirection(gameState.getDirection() + (float) Math.toRadians(turnRate));
                break;
        }

        switch (hidState.getLeftUD()) {
            case UP:
                gameState.setSpeed(gameState.getSpeed() + acceleration);
                break;
            case DOWN:
                gameState.setSpeed(gameState.getSpeed() - acceleration);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        switch (key) {
            case 37:
                gameState.setDirection(gameState.getDirection() - (float) Math.toRadians(turnRate));
                break;
            case 39:
                gameState.setDirection(gameState.getDirection() + (float) Math.toRadians(turnRate));
                break;
            case 61:
            case 107:
                gameState.setSpeed(gameState.getSpeed() + acceleration);
                break;
            case 45:
            case 109:
                gameState.setSpeed(gameState.getSpeed() - acceleration);
                break;
        }
    }
}
