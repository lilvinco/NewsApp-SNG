package com.startng.newsapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesRepo {

    private LiveData<List<NoteBook>> noteBooks;
    private MyNoteDao myNoteDao;

    NotesRepo(Application application) {

        NotesAppDB db = NotesAppDB.getNotesAppDB(application);
        myNoteDao = db.getDao();
        noteBooks = myNoteDao.getAllNotes();
    }

    LiveData<List<NoteBook>> getNoteBooks() {
        return noteBooks;
    }

    void insert(NoteBook noteBook) {
        NotesAppDB.dbExecutor.execute(() -> {
            myNoteDao.insert(noteBook);
        });
    }

    void update(NoteBook noteBook){
        NotesAppDB.dbExecutor.execute(() ->{
            myNoteDao.update(noteBook);
        });
    }

    void delete(NoteBook noteBook){
        NotesAppDB.dbExecutor.execute(() ->{
            myNoteDao.delete(noteBook);
        });
    }

    void delete2(NoteBook... noteBook){
        NotesAppDB.dbExecutor.execute(() ->{
            myNoteDao.delete2(noteBook);
        });
    }

    void clearTable(){
        NotesAppDB.dbExecutor.execute(() ->{
            myNoteDao.clearTable();
        });
    }
}
