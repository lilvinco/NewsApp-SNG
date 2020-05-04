package com.startng.newsapp.Models;

public class NotesModel {

    String title;
    String content;

    // Firestore needs empty constructor

    public NotesModel() {
    }

    public NotesModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
