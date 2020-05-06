package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static android.os.Build.VERSION_CODES.N;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "note")
    String mNote;

    public Note(String note) {
        this.mNote = note;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getNote() {
        return mNote;
    }
}
