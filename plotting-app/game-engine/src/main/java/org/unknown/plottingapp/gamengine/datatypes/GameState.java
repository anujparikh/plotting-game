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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }
}
