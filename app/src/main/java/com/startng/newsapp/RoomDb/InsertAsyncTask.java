package com.startng.newsapp.RoomDb;

import android.os.AsyncTask;
import android.util.Log;

import com.startng.newsapp.NoteModel;

public class InsertAsyncTask extends AsyncTask<NoteModel, Void, Void> {
    private static final String TAG = "InsertAsyncTask";
    private NoteDao mNoteDao;

    public InsertAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(NoteModel... notes) {
        Log.d(TAG, "doInBackgroungThread: " + Thread.currentThread().getName());
        mNoteDao.insertNotes(notes);
        return null;
    }
}
