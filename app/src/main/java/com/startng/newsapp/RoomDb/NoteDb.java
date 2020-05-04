package com.startng.newsapp.RoomDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.startng.newsapp.NoteModel;

@Database(entities = {NoteModel.class}, version = 1)
public abstract class NoteDb extends RoomDatabase {
    private static final String NOTE_DATABASE = "notes_db";
    private static NoteDb instance;
    static NoteDb getInstance(final Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDb.class, NOTE_DATABASE).build();
        }
        return instance;
    }

    public abstract NoteDao getNoteDao();

}
