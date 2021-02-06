package org.unknown.plottingapp.gamengine.datatypes;

public class GameState {
    private int x;
    private int y;
    private double theta;
    private double velocity;

    public GameState(int x, int y, double theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public synchronized double getVelocity() {
        return velocity;
    }

    public synchronized void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public synchronized int getY() {
        return y;
    }

    public synchronized void setY(int y) {
        this.y = y;
    }

    public synchronized double getTheta() {
        return theta;
    }

    public synchronized void setTheta(double theta) {
        this.theta = theta;
    }
}
