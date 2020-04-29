package com.startng.newsapp.Database.DataAccessObject;

import com.startng.newsapp.Database.Model.Note;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM note_table ORDER BY time_stamp DESC")
    LiveData<List<Note>> getAllNotes();

    /*@Query("SELECT * FROM note_table ORDER BY time_stamp")
    LiveData<List<Note>> getAllNotes();*/

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table WHERE nid IN (:noteIds)")
    LiveData<List<Note>> loadAllByIds(int[] noteIds);

    @Query("SELECT * FROM note_table WHERE note_title LIKE :title LIMIT 1")
    LiveData<List<Note>> findByTitle(String title);


}
