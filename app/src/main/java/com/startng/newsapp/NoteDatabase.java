package com.startng.newsapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = { Note.class }, version = 1,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao getNoteDao();

    private static NoteDatabase noteDB;
    private static volatile NoteDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static NoteDatabase getDatabase(Context context) {
        if (null == noteDB) {
            noteDB = buildDatabaseInstance(context);
        }
        return noteDB;
    }

    private static NoteDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                NoteDatabase.class, "note_database")
                .allowMainThreadQueries().build();
    }



    public void cleanUp(){
        noteDB = null;
    }

}
