package com.startng.newsapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MNote implements Parcelable {
    private String noteTitle;
    private String noteText;
    private static int counterOfIndex = 0;
    private int noteIndex;

    public MNote (String note_text) {
        noteTitle = "No title";
        noteText = note_text;
        counterOfIndex++;
        noteIndex = counterOfIndex;

    }

    @Override
    public String toString(){
        return noteTitle +" "+ noteText;
    }

    @Override
    public boolean equals(Object o){
        MNote otherNote = (MNote)o;
        if(this.noteTitle.equals(otherNote.noteTitle) && this.noteText.equals(otherNote.noteText)){
            return true;
        }
        return false;
    }

    public MNote (String note_title, String note_text) {
        noteTitle = note_title;
        noteText = note_text;
        counterOfIndex++;
        noteIndex = counterOfIndex;
    }

    public MNote (Parcel src) {
        noteTitle = src.readString();
        noteText = src.readString();
        noteIndex = src.readInt();
    }

    public static final Parcelable.Creator<MNote> CREATOR = new Parcelable.Creator<MNote>() {
        @Override
        public MNote createFromParcel(Parcel in) {
            return new MNote(in);
        }

        @Override
        public MNote[] newArray(int size) {
            return new MNote[size];
        }
    };

    public String getNoteTitle() { return this.noteTitle;}

    public String getNoteText() { return this.noteText;}

    public int getNoteIndex() { return this.noteIndex;}

    public static ArrayList<MNote> getNotes() {
        ArrayList<MNote> result = new ArrayList<MNote>();
        result.add(new MNote("Plugin Update Recommended!"));
        result.add(new MNote("Android Components", "User Interface, Java Code, Data source etc"));
        result.add(new MNote("It's good to be home!"));
        result.add(new MNote("Software Engineering", "this is a very interesting profession. See SWEBOK 3.0"));
        result.add(new MNote("Petroleum Eng.", "Study is important, Practise moreso"));
        result.add(new MNote("Workman", "study to show thyself approved!"));
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.noteTitle);
        dest.writeString(this.noteText);
        dest.writeInt(this.noteIndex);
    }
}
