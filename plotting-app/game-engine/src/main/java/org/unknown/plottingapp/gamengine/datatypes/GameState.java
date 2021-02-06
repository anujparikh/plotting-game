package org.unknown.plottingapp.gamengine.datatypes;

public class GameState {
    private int x;
    private int y;
    private double theta;

    public GameState(int x, int y, double theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized void setX(int x) {
        this.x = x;
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
