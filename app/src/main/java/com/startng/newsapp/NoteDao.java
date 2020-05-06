package com.startng.newsapp;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.startng.newsapp.util.Constants;


import java.util.List;


@Dao
public interface NoteDao {

    @Query("SELECT * FROM " + Constants.NOTE_TABLE)
    List<Note> getNotes();

    @Insert
    long insertNote(Note note);

    @Update
    void updateNote(Note repos);


    @Delete
    void deleteNote(Note note);

    @Delete
    void deleteNote(Note... note);

}
