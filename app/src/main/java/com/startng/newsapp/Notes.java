package com.startng.newsapp;

public class Notes {
    private String noteTitle;
    private String noteContent;

    public Notes(){

    }

    public Notes(String noteTitle, String noteContent){
        this.noteContent = noteContent;
        this.noteTitle = noteTitle;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

}
