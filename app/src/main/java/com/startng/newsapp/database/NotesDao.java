package com.startng.newsapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDao {

    //Create operation
    @Insert
    void insert(Notes notes);

    //Read operation
    @Query("SELECT * FROM notes_table ORDER BY noteTime DESC ")
    LiveData<List<Notes>> getOrderedNotes();

    //Read single operation
    @Query("SELECT * FROM notes_table WHERE id = :id")
    LiveData<Notes> getSingleNote(int id);

    //Update operation
    @Update()
    void updateNote(Notes notes);

    //Delete operation
    @Delete
    void deleteNote(Notes notes);
}
