package com.startng.newsapp;

import android.provider.BaseColumns;

public class NoteDBContract {
    static class NoteEntry implements BaseColumns {
        static final String TABLE_NAME = "Notes";
        static final String COLUMN_ID = _ID;
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_CONTENT = "content";

        static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_CONTENT + " TEXT NOT NULL)";

        static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
