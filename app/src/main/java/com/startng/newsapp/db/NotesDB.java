package com.startng.newsapp.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.startng.newsapp.Note;

@Database(entities = Note.class, version = 2)
public abstract class NotesDB extends RoomDatabase {

    public abstract NotesDao notesDao();

    private static NotesDB instance;

    public static NotesDB getInstance(Context context){
        if (instance==null)
            instance = Room.databaseBuilder(context, NotesDB.class, "DATABASE_NAME")
                    .allowMainThreadQueries().build();
        return instance;

    }

}
