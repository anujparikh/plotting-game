package org.unknown.plottingengine.gamerecorder.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Records", schema = "public")
public class GameRecord {
    @Id
    @GeneratedValue
    private long id;

    private int x;

    private int y;

    private double direction;

    private double speed;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private GameSession gameSession;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", speed=" + speed +
                '}';
    }
}
