package com.startng.newsapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Notes.class},version = 1,exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class NotesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "notes_database";
    private static volatile NotesDatabase INSTANCE;
    private static final int NO_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NO_OF_THREADS);

    static NotesDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (NotesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NotesDatabase.class,
                            NotesDatabase.DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract NotesDao notesDao();
}

