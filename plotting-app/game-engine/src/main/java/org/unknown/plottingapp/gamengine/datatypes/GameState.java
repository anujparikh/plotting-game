package org.unknown.plottingapp.gamengine.datatypes;

public class GameState {
    private float x;
    private float y;
    private float theta;
    private float velocity;

    public GameState(float x, float y, float theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public synchronized float getVelocity() {
        return velocity;
    }

    public synchronized void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public synchronized float getX() {
        return x;
    }

    public synchronized void setX(float x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public synchronized float getY() {
        return y;
    }

    public synchronized void setY(float y) {
        this.y = y;
    }

    public synchronized float getTheta() {
        return theta;
    }

    public synchronized void setTheta(float theta) {
        this.theta = theta;
    }
}
