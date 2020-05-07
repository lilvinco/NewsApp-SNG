package com.startng.newsapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyNoteDao {

    @Query("SELECT * FROM table_1")
    LiveData<List<NoteBook>> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(NoteBook noteBook);

    @Update
    public void update(NoteBook noteBook);

    @Delete
    public void delete(NoteBook noteBook);

    @Delete
    public void delete2(NoteBook... noteBook);

    @Query("delete from table_1")
    void clearTable();
}
