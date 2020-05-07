package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int noteId;

    @NonNull
    @ColumnInfo(name = "noteTitle")
    private String noteTitle;

    @NonNull
    @ColumnInfo(name = "noteContent")
    private String noteContent;

    public Note(int noteId, @NonNull String noteTitle, @NonNull String noteContent) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    @NonNull
    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(@NonNull String noteTitle) {
        this.noteTitle = noteTitle;
    }

    @NonNull
    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(@NonNull String noteContent) {
        this.noteContent = noteContent;
    }
}
