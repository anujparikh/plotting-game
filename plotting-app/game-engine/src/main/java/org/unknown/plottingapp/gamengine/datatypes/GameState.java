package org.unknown.plottingapp.gamengine.datatypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameState {
    private final Position currentPosition;
    private final List<Position> positionHistory;
    private final int maxHistoryBufferSize;
    private float theta;
    private float velocity;

    public GameState(float x, float y, float theta, int maxHistoryBufferSize) {
        this.currentPosition = new Position(x, y);
        this.maxHistoryBufferSize = maxHistoryBufferSize;
        this.positionHistory = Collections.synchronizedList(new ArrayList<>());
        this.theta = theta;
    }

    public synchronized float getVelocity() {
        return velocity;
    }

    public synchronized void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public synchronized void setLocation(float x, float y) {
        this.currentPosition.x = x;
        this.currentPosition.y = y;
        this.positionHistory.add(new Position(this.currentPosition));
        if (this.positionHistory.size() > this.maxHistoryBufferSize) {
            this.positionHistory.remove(0);
        }
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public List<Position> getPositionHistory() {
        return positionHistory;
    }

    public synchronized float getTheta() {
        return theta;
    }

    public synchronized void setTheta(float theta) {
        this.theta = theta;
    }
}
