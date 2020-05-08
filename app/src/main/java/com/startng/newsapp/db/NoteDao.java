package com.startng.newsapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.startng.newsapp.HeadlinesActivity;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote (HeadlinesActivity note);

    @Delete
    void deleteNote (HeadlinesActivity... note);

    @Update
    void updateNote (HeadlinesActivity note);

    @Query("SELECT * FROM notes")
    List<HeadlinesActivity> getNotes();

    @Query("SELECT * FROM notes WHERE id = :noteId")
    HeadlinesActivity getNoteById(int noteId);

    @Query("DELETE FROM notes WHERE id = :noteId")
    void deleteNoteById(int noteId);
}
