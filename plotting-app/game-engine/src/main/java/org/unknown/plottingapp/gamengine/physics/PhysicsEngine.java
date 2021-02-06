package org.unknown.plottingapp.gamengine.physics;

import org.unknown.plottingapp.gamengine.datatypes.GameState;

public class PhysicsEngine implements Runnable {

    private final GameState gameState;
    private final int delay;
    private final int mapWidth;
    private final int mapHeight;
    private final double MAX_VEL = 50;
    private final double MIN_VEL = 10;


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

    private double capVelocity(double desiredVelocity) {
        return Math.min(Math.abs(desiredVelocity), MAX_VEL) * Math.signum(desiredVelocity);
    }

    private void updateState() {
        // approximation below. Assume game tick is perfectly timed at an interval of size 'delay' milliseconds.
        this.gameState.setVelocity(Math.min(Math.max(this.gameState.getVelocity(), MIN_VEL), MAX_VEL));
        double xDist = this.gameState.getVelocity() * Math.sin(this.gameState.getTheta()) * (delay / 1000.0);
        double yDist = -this.gameState.getVelocity() * Math.cos(this.gameState.getTheta()) * (delay / 1000.0);

        gameState.setX(wrap((int) Math.round(gameState.getX() + xDist), this.mapWidth));
        gameState.setY(wrap((int) Math.round(gameState.getY() + yDist), this.mapHeight));
    }

    private int wrap(int x, int lim) {
        return ((x % lim) + lim) % lim;
    }
}
