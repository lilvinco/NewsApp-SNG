package com.startng.newsapp;

public class Notes {
    private String noteTitle;
    private String noteContent;
    private Long timeMillSeconds;

    public Notes(String noteTitle, String noteContent){
        this.noteContent = noteContent;
        this.noteTitle = noteTitle;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Long getTimeMillSeconds() {
        return timeMillSeconds;
    }

    public void setTimeMillSeconds(Long timeMillSeconds) {
        this.timeMillSeconds = timeMillSeconds;
    }
}
