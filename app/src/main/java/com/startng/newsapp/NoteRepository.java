package com.startng.newsapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.RoomDatabase;

import java.util.List;

public class NoteRepository {
    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllWords;

    public NoteRepository(Application application) {
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        mNoteDao = db.noteDao();
        mAllWords = mNoteDao.getAllNotes();
    }
    LiveData<List<Note>> getAllWords() {
        return mAllWords;
    }
    void insert(Note note) {
        //NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
          //  mNoteDao.insert(note);
            new InsertNoteAsyncTask(mNoteDao).execute(note);
        }
    void update(Note note){
        new UpdateNoteAsyncTask(mNoteDao).execute(note);

    }
    void delete(Note note){
        new DeleteNoteAsyncTask(mNoteDao).execute(note);

    }
    void deleteAll(){
        new DeleteAllNoteAsyncTask(mNoteDao).execute();

    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;
        private  InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;
        private  UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.updateNote(notes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;
        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteNote(notes[0]);
            return null;
        }
    }
    private static class DeleteAllNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;
        private  DeleteAllNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteAll();
            return null;
        }
    }
   /* public LiveData<List<Note>> getmAllWords(){
        return mAllWords;
    }*/

}
