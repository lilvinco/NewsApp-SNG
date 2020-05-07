package com.startng.newsapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.startng.newsapp.NoteDBContract.*;

public class DataManager {

    public static ArrayList<Notes> fetchAllNotes(NoteDBHelper helper) {
        ArrayList<Notes> notes = new ArrayList<>();
        SQLiteDatabase database = helper.getWritableDatabase();

        String[] columns = {NoteEntry.COLUMN_ID,
                NoteEntry.COLUMN_TITLE,
                NoteEntry.COLUMN_CONTENT};

        Cursor cursor = database.query(
                NoteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        int positionID = cursor.getColumnIndex(NoteEntry.COLUMN_ID);
        int positionTitle = cursor.getColumnIndex(NoteEntry.COLUMN_TITLE);
        int positionContent = cursor.getColumnIndex(NoteEntry.COLUMN_CONTENT);


        while (cursor.moveToNext()) {
            int ID = cursor.getInt(positionID);
            String title = cursor.getString(positionTitle);
            String content = cursor.getString(positionContent);

            notes.add(new Notes(ID, title, content));
        }
        cursor.close();
        return notes;
    }

    public static Notes fetchEmployee(NoteDBHelper helper, int noteID) {
        SQLiteDatabase database = helper.getWritableDatabase();
        Notes notes = null;
        String[] columns = {NoteEntry.COLUMN_ID,
                NoteEntry.COLUMN_TITLE,
                NoteEntry.COLUMN_CONTENT};

        String selection = NoteEntry.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(noteID)};

        Cursor cursor = database.query(
                NoteEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int positionID = cursor.getColumnIndex(NoteEntry.COLUMN_ID);
        int positionTitle = cursor.getColumnIndex(NoteEntry.COLUMN_TITLE);
        int positionContent = cursor.getColumnIndex(NoteEntry.COLUMN_CONTENT);


        while (cursor.moveToNext()) {
            int ID = cursor.getInt(positionID);
            String title = cursor.getString(positionTitle);
            String content = cursor.getString(positionContent);

            notes = new Notes(ID, title, content);
        }
        cursor.close();
        return notes;
    }


}
