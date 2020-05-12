package com.startng.newsapp;

public class MyNotes {
    private int ID;
    private String noteTitle;
    private String noteContent;

    public MyNotes() {
    }

    public MyNotes(int ID, String noteTitle, String noteContent){
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.ID = ID;
    }

    public MyNotes(String noteTitle, String noteContent){
        this.noteContent = noteContent;
        this.noteTitle = noteTitle;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public int getID() {
        return ID;
    }
}
