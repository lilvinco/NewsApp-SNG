package com.startng.newsapp;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Update;

import java.util.List;

public class NotesRepository {
    private NoteDao noteDao;
    private LiveData<List<Notes>> allNotes;

    public NotesRepository(Context context){
        NotesDatabse database = NotesDatabse.getInstance(context);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public  void insert( Notes note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Notes note){
        new  UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Notes note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void  deleteAllNotes(){
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Notes>> getAllNotes(){
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Notes,Void,Void>{
            private NoteDao noteDao;
            private InsertNoteAsyncTask(NoteDao noteDao){
                this.noteDao = noteDao;
            }
        @Override
        protected Void doInBackground(Notes... notes) {
                noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NoteDao noteDao;
        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Notes... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NoteDao noteDao;
        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Notes... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;
        private DeleteAllNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
