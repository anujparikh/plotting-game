package org.unknown.plottingengine.gamerecorder.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
*
CREATE TABLE public."Records"
(
    id bigserial PRIMARY KEY,
    x integer NOT NULL,
    y integer NOT NULL,
    velocity double precision NOT NULL,
    heading double precision NOT NULL,
	created_time timestamp default current_timestamp,
    session_id bigint NOT NULL,
    CONSTRAINT valid_session_id_constraint FOREIGN KEY (session_id)
            REFERENCES public."Sessions" (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE CASCADE
            NOT VALID
)

*
*
* */

@Entity
@Table(name = "Records", schema = "public")
public class GameRecord {
    @Id
    @GeneratedValue
    private long id;

    private int x;

    private int y;

    private double heading;

    private double velocity;

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

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }
}
