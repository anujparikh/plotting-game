package org.unknown.plottingapp.gamengine.datatypes;

public class GameState {
    private final Position currentPosition;
    private float theta;
    private float velocity;

    public GameState(float x, float y, float theta) {
        this.currentPosition = new Position(x, y);
        this.theta = theta;
    }

    public synchronized float getVelocity() {
        return velocity;
    }

    public synchronized void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public synchronized void setLocation(float x, float y) {
        this.currentPosition.setX(x);
        this.currentPosition.setY(y);
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public synchronized float getTheta() {
        return theta;
    }

    public synchronized void setTheta(float theta) {
        this.theta = theta;
    }
}
