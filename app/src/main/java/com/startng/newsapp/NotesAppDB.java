package com.startng.newsapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {NoteBook.class}, version = 1, exportSchema = false)
public abstract class NotesAppDB extends RoomDatabase {

    public abstract MyNoteDao getDao();

    private static NotesAppDB notesAppDB;

    private static final int numberOfThreads = 4;
    static final ExecutorService dbExecutor = Executors.newFixedThreadPool(numberOfThreads);

    static NotesAppDB getNotesAppDB(Context context) {

        if (notesAppDB == null){
            synchronized (NotesAppDB.class) {
                if (notesAppDB == null){
                    notesAppDB = Room.databaseBuilder(context.getApplicationContext(), NotesAppDB.class, "database-name").build();
                }
            }
        }
        return notesAppDB;
    }

}
