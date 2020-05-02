package com.startng.newsapp.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private LiveData<List<Notes>> allNotes;
    private NotesRepository notesRepository;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepository = new NotesRepository(application);
        allNotes = notesRepository.getAllNotes();
    }

    //CREATE
    public void insertNote(Notes notes){
        notesRepository.insertNote(notes);
    }
    //READ
    public LiveData<List<Notes>> getAllNotes(){
        return allNotes;
    }
    //READ SINGLE NOTE BY ID
    public LiveData<Notes> getSingleNote(int id){
        return notesRepository.getSingleNote(id);
    }
    //UPDATE
    public void updateNote(Notes notes){
        notesRepository.updateNote(notes);
    }
    //DELETE
    public void deleteNote(Notes notes){
        notesRepository.deleteNote(notes);
    }
}
