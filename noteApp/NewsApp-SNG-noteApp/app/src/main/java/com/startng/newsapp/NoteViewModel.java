package com.startng.newsapp;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NoteViewModel extends ViewModel {

    NotesRepository repository;
    LiveData<List<Notes>>allNotes;

    public NoteViewModel(Context context) {
        repository = new NotesRepository(context);
        allNotes = repository.getAllNotes();

    }

    public NoteViewModel(){

    }

    public  void insert(Notes note){
        repository.insert(note);
    }

    public  void delete(Notes note){
        repository.delete(note);
    }

    public  void update(Notes note){
        repository.update(note);
    }

    public  void deleteAllNotes(){
        repository.deleteAllNotes();
    }



    public LiveData<List<Notes>> getAllNotes() {
        return allNotes;
    }
}
