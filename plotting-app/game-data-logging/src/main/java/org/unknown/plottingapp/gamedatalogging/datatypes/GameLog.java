package org.unknown.plottingapp.gamedatalogging.datatypes;

public class GameLog {
    private final int x;
    private final int y;
    private final float theta;
    private final float velocity;

    public GameLog(int x, int y, float theta, float velocity) {
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.velocity = velocity;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getTheta() {
        return theta;
    }

    public float getVelocity() {
        return velocity;
    }

    @Override
    public String toString() {
        return "GameLog[" +
                "x=" + x +
                ", y=" + y +
                ", theta=" + theta +
                ", velocity=" + velocity +
                ']';
    }
}
