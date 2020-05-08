package com.startng.newsapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.startng.newsapp.HeadlinesActivity;

@Database(entities = HeadlinesActivity.class, version = 1)
public abstract class NoteDB extends RoomDatabase {
    public abstract NoteDao noteDao();

    public static final String DATABSE_NAME = "noteDb";

    private static NoteDB instance;

    public static NoteDB getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, NoteDB.class, DATABSE_NAME)
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }
}
