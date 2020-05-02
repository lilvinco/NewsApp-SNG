package com.startng.newsapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notes_table")
public class Notes {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date noteTime;
    private String noteDesc;
    private int noteColor;

    public Notes(int id, @NonNull Date noteTime, @NonNull String noteDesc, int noteColor) {
        this.id = id;
        this.noteTime = noteTime;
        this.noteDesc = noteDesc;
        this.noteColor = noteColor;
    }

    @Ignore
    public Notes(@NonNull Date noteTime, @NonNull String noteDesc, int noteColor) {
        this.noteTime = noteTime;
        this.noteDesc = noteDesc;
        this.noteColor = noteColor;
    }

    @Ignore
    public Notes() {
    }

    //Getter and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public Date getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(@NonNull Date noteTime) {
        this.noteTime = noteTime;
    }

    @NonNull
    public String getNoteDesc() {
        return noteDesc;
    }

    public void setNoteDesc(@NonNull String noteDesc) {
        this.noteDesc = noteDesc;
    }

    public int getNoteColor() {
        return noteColor;
    }

    public void setNoteColor(int noteColor) {
        this.noteColor = noteColor;
    }
}
