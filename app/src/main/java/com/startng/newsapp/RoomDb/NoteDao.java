package com.startng.newsapp.RoomDb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.startng.newsapp.NoteModel;
import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes")
    LiveData<List<NoteModel>> getNotes();

    @Update
    int update(NoteModel... notes);

    @Delete
    int delete(NoteModel... notes);

    @Insert
    long[] insertNotes(NoteModel... notes);


}
