package com.startng.newsapp.RoomDb;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.startng.newsapp.NoteModel;

import java.util.List;

public class NoteRepository {
    private NoteDb mNoteDatabase;
    public NoteRepository(Context context){
        mNoteDatabase = NoteDb.getInstance(context);
    }

    public void deleteNote(NoteModel note){
        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }
    public void updateNote(NoteModel note){
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void insertNotesTask(NoteModel note){
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public LiveData<List<NoteModel>> retrieveNotesTask(){
        return mNoteDatabase.getNoteDao().getNotes();}

}
