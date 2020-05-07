package com.startng.newsapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class, version = 2, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();
    private static volatile NoteRoomDatabase noteRoomDatabaseInstance;

    static NoteRoomDatabase getDatabase(final Context context) {
        if (noteRoomDatabaseInstance == null) {
            synchronized (NoteRoomDatabase.class) {
                if (noteRoomDatabaseInstance == null) {
                    noteRoomDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "notes")
                            .addMigrations(new Migration(1,2) {
                                @Override
                                public void migrate(@NonNull SupportSQLiteDatabase database) {
                                    database.execSQL("DROP TABLE `notes`");
                                    database.execSQL("CREATE TABLE `notes` (`noteId` INTEGER NOT NULL, `noteTitle` TEXT NOT NULL, `noteContent` TEXT NOT NULL, PRIMARY KEY(`noteId`))");
                                }
                            })
                            .build();
                }
            }
        }
        return noteRoomDatabaseInstance;
    }
}
