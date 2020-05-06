package com.startng.newsapp.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.startng.newsapp.Note;
import com.startng.newsapp.NoteDao;
import com.startng.newsapp.util.Constants;




@Database(entities = { Note.class }, version = 1)


public abstract class MyDatabase extends RoomDatabase {

    public abstract NoteDao getNoteDao();


    private static MyDatabase noteDB;


    public static  MyDatabase getInstance(Context context) {
        if (null == noteDB) {
            noteDB = buildDatabaseInstance(context);
        }
        return noteDB;
    }

    private static MyDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                MyDatabase.class,
                Constants.DB_NAME).allowMainThreadQueries().build();
    }

    public  void cleanUp(){
        noteDB = null;
    }
}