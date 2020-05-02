package com.startng.newsapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesRepository {
    private NotesDao notesDao;
    private LiveData<List<Notes>> allNotes;

    public NotesRepository(Application application) {
        NotesDatabase notesDatabase = NotesDatabase.getDatabase(application);
        notesDao = notesDatabase.notesDao();
        allNotes = notesDao.getOrderedNotes();
    }

    //Create operation
    public void insertNote(final Notes notes){
        NotesDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                notesDao.insert(notes);
            }
        });
    }

    //Read operation
    public LiveData<List<Notes>> getAllNotes(){
        return allNotes;
    }

    //Read single operation
    public LiveData<Notes> getSingleNote(final int id){
        return notesDao.getSingleNote(id);
    }

    //Update operation
    public void updateNote(final Notes notes){
        NotesDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                notesDao.updateNote(notes);
            }
        });
    }

    //Delete operation
    public void deleteNote(final Notes notes){
        NotesDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                notesDao.deleteNote(notes);
            }
        });
    }
}
