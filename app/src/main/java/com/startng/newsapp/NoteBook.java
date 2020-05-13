package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "table_1")
public class NoteBook implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int NoteNo;

    @ColumnInfo(name = "Note_Title")
    private String title;
    @ColumnInfo(name = "Note_Content")
    private String content;
    //private Date date;

    public NoteBook(String title, String content) {
        this.title = title;
        this.content = content;
        //this.date = new Date(System.currentTimeMillis());
    }

   /* public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }*/

    public int getNoteNo() {
        return NoteNo;
    }

    public void setNoteNo(int noteNo) {
        this.NoteNo = noteNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitles(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NoteBook{" +
                "NoteNo=" + NoteNo +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
