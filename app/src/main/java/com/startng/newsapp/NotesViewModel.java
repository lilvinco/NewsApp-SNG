package com.startng.newsapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private NotesRepo notesRepo;
    private LiveData<List<NoteBook>> noteBooks;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepo = new NotesRepo(application);
        noteBooks = notesRepo.getNoteBooks();
    }

    LiveData<List<NoteBook>> getNoteBooks(){
        return noteBooks;
    }

    void insert(NoteBook noteBook){
        notesRepo.insert(noteBook);
    }

    void update(NoteBook noteBook){
        notesRepo.update(noteBook);
    }

    void delete(NoteBook noteBook){
        notesRepo.delete(noteBook);
    }

    void delete2(NoteBook... noteBook){
        notesRepo.delete2(noteBook);
    }

    void clearTable(){
        notesRepo.clearTable();
    }
}
