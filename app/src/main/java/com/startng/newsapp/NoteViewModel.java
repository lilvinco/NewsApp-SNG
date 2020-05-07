package com.startng.newsapp;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase noteRoomDatabase;
    private LiveData<List<Note>> listOfNotes;

    public NoteViewModel(Application application) {
        super(application);

        noteRoomDatabase = NoteRoomDatabase.getDatabase(application);
        noteDao = noteRoomDatabase.noteDao();
        listOfNotes = noteDao.getAllNotes();
    }

    LiveData<Note> getNote(int noteId) {
        return noteDao.getNote(noteId);
    }

    LiveData<List<Note>> getListOfNotes() {
        return listOfNotes;
    }

    private class OperationsAsyncTask extends AsyncTask<Note, Void, Void> {

        NoteDao mAsyncTaskDao;

        OperationsAsyncTask(NoteDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            return null;
        }
    }

    public void insert(Note note) {
        new InsertAsyncTask(noteDao).execute(note);
    }

    private class InsertAsyncTask extends OperationsAsyncTask {

        InsertAsyncTask(NoteDao mNoteDao) {
            super(mNoteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.insert(notes[0]);
            return null;
        }
    }

    public void update(Note note) {
        new UpdateAsyncTask(noteDao).execute(note);
    }

    private class UpdateAsyncTask extends OperationsAsyncTask {

        UpdateAsyncTask(NoteDao noteDao) {
            super(noteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.update(notes[0]);
            return null;
        }
    }

    public void delete(Note note) {
        new DeleteAsyncTask(noteDao).execute(note);
    }

    private class DeleteAsyncTask extends OperationsAsyncTask {

        DeleteAsyncTask(NoteDao noteDao) {
            super(noteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.delete(notes[0]);
            return null;
        }
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(noteDao).execute();
    }

    private class DeleteAllAsyncTask extends OperationsAsyncTask {

        DeleteAllAsyncTask(NoteDao dao) {
            super(dao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }
}
