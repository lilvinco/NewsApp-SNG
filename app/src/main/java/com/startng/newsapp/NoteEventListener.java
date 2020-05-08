package com.startng.newsapp;



public interface NoteEventListener {

    void onNoteClick(Note note);
    void onNoteLongClick(Note note);
}
