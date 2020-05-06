package com.startng.newsapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.startng.newsapp.util.Constants;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = Constants.NOTE_TABLE)
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "note_body")

    private  String heading;

    private String body;


    public  Note(String heading,String body){
        this.heading = heading;
        this.body = body;
    }





@Ignore
 public Note(){

}

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;

        Note note = (Note) o;

        if (id != note.id) return false;
        return Objects.equals(heading, note.heading);
    }



    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (heading != null ? heading.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", heading='" + heading + '\'' +
                '}';
    }
}

