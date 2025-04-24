package com.example.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getIsdone() {
        return isdone;
    }

    public void setIsdone(Boolean isdone) {
        this.isdone = isdone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String dateTime) {
        this.date = dateTime;
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
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Task(Boolean isdone, String date,String time, String description, String title) {
        this.isdone = isdone;
        this.date = date;
        this.time=time;
        this.description = description;
        this.title = title;
    }
@PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String date;



    private String time;
    private Boolean isdone;


}
