package com.startng.newsapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Query("SELECT * FROM notes ORDER BY noteId DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM notes WHERE noteId=:noteId")
    LiveData<Note> getNote(int noteId);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
