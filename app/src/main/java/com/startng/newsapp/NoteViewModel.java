package com.startng.newsapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository mRepository;

    private LiveData<List<Note>>  mAllWords;

    public NoteViewModel (Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<Note>> getAllWords() { return mAllWords; }

    public void insert(Note note) { mRepository.insert(note); }
    public void update(Note repos) { mRepository.update(repos); }
    public void delete (Note note) { mRepository.delete(note); }
    public void deleteAll (Note note) { mRepository.deleteAll(note); }
}