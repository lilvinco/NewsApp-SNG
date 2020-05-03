package com.startng.newsapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository mRepository;
    private LiveData<List<Note>> mAllWords;

    public NoteViewModel(Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mAllWords = mRepository.getAllWords();
    }
    LiveData<List<Note>> getAllWords() {
        return mAllWords;
    }
    void insert(Note note) {
        mRepository.insert(note);
    }
    void update(Note note) {
        mRepository.update(note);
    }
    void delete(Note note) {
        mRepository.delete(note);
    }
    void deleteAll(Note note) {
        mRepository.deleteAll();
    }
}
