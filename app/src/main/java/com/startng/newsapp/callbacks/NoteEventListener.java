package com.startng.newsapp.callbacks;

import com.startng.newsapp.Note;

public interface NoteEventListener {
    void onNoteClick(Note note);

    void onNoteLongClick(Note note);
}
