package org.unknown.plottingengine.gamerecorder.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;


@Entity
@Table(name = "Sessions", schema = "public")
public class GameSession {

    private String title;

    @Id
    @GeneratedValue
    private long id;

    @CreationTimestamp
    private Timestamp startTime;

    private Timestamp endTime;

    public GameSession() {
    }

    public long getId() {
        return id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
