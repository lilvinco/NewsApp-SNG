package com.startng.newsapp.Database.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int nid;

    @ColumnInfo(name = "note_title")
    private String Note_Title;

    @ColumnInfo(name = "note_text")
    private String Note_Text;

    @ColumnInfo(name = "time_stamp")
    private String Time_Stamp;

    public Note(String Note_Title, String Note_Text, String Time_Stamp) {
        this.Note_Title = Note_Title;
        this.Note_Text = Note_Text;
        this.Time_Stamp = Time_Stamp;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getNote_Title() {
        return Note_Title;
    }

    public void setNote_Title(String note_Title) {
        this.Note_Title = note_Title;
    }

    public String getNote_Text() {
        return Note_Text;
    }

    public void setNote_Text(String note_Text) {
        this.Note_Text = note_Text;
    }

    public String getTime_Stamp() {
        return Time_Stamp;
    }

    public void setTime_Stamp(String time_Stamp) {
        this.Time_Stamp = time_Stamp;
    }
}

