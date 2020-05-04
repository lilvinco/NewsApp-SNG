package com.startng.newsapp;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class NoteActivityViewModel extends ViewModel {
    public static final String ORIGINAL_NOTE_TITLE = "com.startng.newsapp.ORIGINAL_NOTE_TITLE";
    public static final String ORIGINAL_NOTE_TEXT = "com.startng.newsapp.ORIGINAL_NOTE_TEXT";

    public String mOriginalNoteTitle;
    public String mOriginalNoteText;
    public boolean mIsNewlyCreated = true;

    public void outState(Bundle outState){
        outState.putString(ORIGINAL_NOTE_TEXT, mOriginalNoteText);
        outState.putString(ORIGINAL_NOTE_TITLE, mOriginalNoteTitle);
    }

    public  void inState(Bundle inState){
        mOriginalNoteText = inState.getString(ORIGINAL_NOTE_TEXT, mOriginalNoteText);
        mOriginalNoteTitle = inState.getString(ORIGINAL_NOTE_TITLE, mOriginalNoteTitle);
    }
}
