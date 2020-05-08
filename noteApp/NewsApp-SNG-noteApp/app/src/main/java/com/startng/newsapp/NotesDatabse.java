package com.startng.newsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Notes.class}, version = 1)
public abstract class NotesDatabse extends RoomDatabase {

    private  static NotesDatabse instance;

    public abstract  NoteDao noteDao();

    public static synchronized  NotesDatabse getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),NotesDatabse.class,
                    "notes_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback =  new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
