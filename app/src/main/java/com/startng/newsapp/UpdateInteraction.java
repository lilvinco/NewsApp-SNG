package com.startng.newsapp;

import java.util.List;

public interface UpdateInteraction {
    void onNewActivity(NoteBook noteBookList);
    void deleteRow(NoteBook deleteNote);
    void deleteRow2(NoteBook... deleteNote);
}
