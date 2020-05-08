package com.startng.newsapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.startng.newsapp.data.NotesContract.NotesEntry;

@Entity(tableName = NotesEntry.TABLE_NAME)
public class Notes {
    @ColumnInfo(name = NotesEntry.COLUMN_TITLE)
   private String title;

   @ColumnInfo(name = NotesEntry.COLUMN_DESCRIPTION)
   private String description;

   @PrimaryKey(autoGenerate = true)
    private int id;

    public Notes(String title,String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }


}
