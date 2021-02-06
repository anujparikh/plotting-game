package org.unknown.plottingapp.gamengine.physics;

import org.unknown.plottingapp.gamengine.datatypes.GameState;

public class PhysicsEngine implements Runnable {

    private final GameState gameState;
    private final int delay;
    private final int mapWidth;
    private final int mapHeight;

    public PhysicsEngine(GameState gameState, int delay, int mapWidth, int mapHeight) {
        this.gameState = gameState;
        this.delay = delay;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    @Override
    public void run() {
        while (true) {
            try {
                updateState();
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateState() {
        // approximation below. Assume game tick is perfectly timed at an interval of size 'delay' milliseconds.
        double xDist = this.gameState.getVelocity() * Math.sin(this.gameState.getTheta()) * (delay / 1000.0);
        double yDist = -this.gameState.getVelocity() * Math.cos(this.gameState.getTheta()) * (delay / 1000.0);

        gameState.setX(wrap((int) Math.round(gameState.getX() + xDist), this.mapWidth));
        gameState.setY(wrap((int) Math.round(gameState.getY() + yDist), this.mapHeight));
    }

    private int wrap(int x, int lim) {
        return ((x % lim) + lim) % lim;
    }
}
