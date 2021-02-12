package org.unknown.plottingapp.gamengine.datatypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameState {
    private final Position currentPosition;
    private final List<Position> positionHistory;
    private final int maxHistoryBufferSize;
    private float direction;
    private float speed;

    public GameState(float x, float y, float direction, int maxHistoryBufferSize) {
        this.currentPosition = new Position(x, y);
        this.maxHistoryBufferSize = maxHistoryBufferSize;
        this.positionHistory = Collections.synchronizedList(new ArrayList<>());
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "GameState[" +
                "x=" + currentPosition.x +
                ", y=" + currentPosition.y +
                ", direction=" + direction +
                ", speed=" + speed +
                ']';
    }

    public synchronized float getSpeed() {
        return speed;
    }

    public synchronized void setSpeed(float speed) {
        this.speed = speed;
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

    public synchronized float getDirection() {
        return direction;
    }

    public synchronized void setDirection(float direction) {
        this.direction = direction;
    }
}
