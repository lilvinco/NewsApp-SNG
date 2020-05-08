package com.startng.newsapp.data;

import android.provider.BaseColumns;

public class NotesContract {
    public static final String CONTENT_AUTHORITY = "";
    public static final String PATH_PETS = "";

    private NotesContract() {}

    public static final class NotesEntry implements  BaseColumns{
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_TITLE ="title";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESCRIPTION = "description";
    }


}
