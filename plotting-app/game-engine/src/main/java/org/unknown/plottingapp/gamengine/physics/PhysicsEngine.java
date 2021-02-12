package org.unknown.plottingapp.gamengine.physics;

import org.unknown.plottingapp.gamengine.datatypes.GameState;

import java.util.logging.Logger;

public class PhysicsEngine implements Runnable {

    private static final Logger logger = Logger.getLogger(PhysicsEngine.class.getName());
    private final GameState gameState;
    private final int delay;
    private final int mapWidth;
    private final int mapHeight;
    private final float MAX_VEL = 100;
    private final float MIN_VEL = 0;


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
                logger.severe(e.getMessage());
            }
        }
    }

    private void updateState() {
        // approximation below. Assume game tick is perfectly timed at an interval of size 'delay' milliseconds.
        this.gameState.setSpeed(Math.min(Math.max(this.gameState.getSpeed(), MIN_VEL), MAX_VEL));
        float xDist = this.gameState.getSpeed() * (float) Math.sin(this.gameState.getDirection()) * (delay / 1000.0F);
        float yDist = -this.gameState.getSpeed() * (float) Math.cos(this.gameState.getDirection()) * (delay / 1000.0F);

        gameState.setLocation(wrap(gameState.getCurrentPosition().x + xDist, this.mapWidth),
                wrap(gameState.getCurrentPosition().y + yDist, this.mapHeight));
    }

    private float wrap(float x, float lim) {
        return ((x % lim) + lim) % lim;
    }
}
