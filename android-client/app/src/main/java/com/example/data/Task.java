package com.example.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class Task {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsdone() {
        return isdone;
    }

    public void setIsdone(Boolean isdone) {
        this.isdone = isdone;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Task(String id, Boolean isdone, LocalDateTime dateTime, String description, String title) {
        this.id = id;
        this.isdone = isdone;
        this.dateTime = dateTime;
        this.description = description;
        this.title = title;
    }
@PrimaryKey(autoGenerate = true)
    private String id;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private Boolean isdone;


}
