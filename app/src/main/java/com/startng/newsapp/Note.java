package com.startng.newsapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*Annotating this java class with @Entity, room will generate all the necessary
* code to create an SQLite table for this object and columns for all it's fields
* */
@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    //Note constructor
    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    //created getters and setters and Room will create all the common codes at runtime
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
