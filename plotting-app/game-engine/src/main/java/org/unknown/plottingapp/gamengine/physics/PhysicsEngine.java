package org.unknown.plottingapp.gamengine.physics;

import org.unknown.plottingapp.gamengine.datatypes.GameState;

public class PhysicsEngine implements Runnable {

    private final GameState gameState;
    private final int delay;
    private final int mapWidth;
    private final int mapHeight;
    private final float MAX_VEL = 50;
    private final float MIN_VEL = 10;


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
        this.gameState.setVelocity(Math.min(Math.max(this.gameState.getVelocity(), MIN_VEL), MAX_VEL));
        float xDist = this.gameState.getVelocity() * (float) Math.sin(this.gameState.getTheta()) * (delay / 1000.0F);
        float yDist = -this.gameState.getVelocity() * (float) Math.cos(this.gameState.getTheta()) * (delay / 1000.0F);

        gameState.setX(wrap(gameState.getX() + xDist, this.mapWidth));
        gameState.setY(wrap(gameState.getY() + yDist, this.mapHeight));
    }

    private float wrap(float x, float lim) {
        return ((x % lim) + lim) % lim;
    }
}
