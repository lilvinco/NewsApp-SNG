package com.startng.newsapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1)
abstract class NoteRoomDatabase extends RoomDatabase {
    abstract NoteDao noteDao();
    private static volatile NoteRoomDatabase INSTANCE;
    //private static final int NUMBER_OF_THREADS = 4;
    //static final ExecutorService databaseWriteExecutor =
            //Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static NoteRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        //private SupportSQLiteDatabase db;

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            //this.db = db;
            super.onOpen(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;
        private PopulateDbAsyncTask(NoteRoomDatabase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Hello world"));
            noteDao.insert(new Note("Hello Nigeria"));

            return null;
        }
    }


            /*databaseWriteExecutor.execute(() -> {
                NoteDao dao = INSTANCE.noteDao();
                dao.deleteAll();

                Note note = new Note("Hello");
                dao.insert(note);
                note = new Note("World");
                dao.insert(note);

                dao.deleteNote(note);

                dao.updateNote(note);
            });
        }
    };*/
}
