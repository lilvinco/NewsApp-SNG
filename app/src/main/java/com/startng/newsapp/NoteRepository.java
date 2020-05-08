package com.startng.newsapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class NoteRepository {
    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllWords;

    NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        mNoteDao = db.getNoteDao();
        mAllWords = mNoteDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Note>> getAllWords() {
        return mAllWords;
    }
    void insert(final Note note) {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.insert(note);
        });
    }
    void update(final Note repos) {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.update(repos);
        });
    }
    void delete(final Note note) {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.delete(note);
        });
    }
    void deleteAll(final Note... note) {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.deleteAll(note);
        });
    }



}
