package com.startng.newsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewNoteActivity extends AppCompatActivity {
    private TextView viewNoteTitle;
    private TextView viewNoteText;
    private int notesIndex;
    private ImageView deleteBtn;
    private ImageView editBtn;
    private MNote noteViewing;
    private static final int TO_EDIT_NOTE = 0x5000;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_note);

        Toolbar toolbar =  findViewById(R.id.view_note_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewNoteTitle = findViewById(R.id.view_note_title);
        viewNoteText = findViewById(R.id.view_note_text);

        final MNote note = getIntent().getParcelableExtra("EXISTING_NOTE");
        noteViewing = note;
        final int notePosition = getIntent().getIntExtra("EXISTING_NOTE_POSITION", 0);
        notesIndex = notePosition;
        String noteTitle = note.getNoteTitle();
        String noteText = note.getNoteText();
        viewNoteTitle.setText(noteTitle);
        viewNoteText.setText(noteText);

        deleteBtn = findViewById(R.id.view_delete_note);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("NOTE_TO_DELETE", note);
                intent.putExtra("NOTE_TO_DELETE_POSITION", notePosition);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

//        editBtn = findViewById(R.id.view_update_note);
//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getBaseContext(), EditNoteActivity.class);
//                intent.putExtra("NOTE_TO_EDIT", note);
//                startActivityForResult(intent, TO_EDIT_NOTE);
//            }
//        });

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == TO_EDIT_NOTE){
//            if(resultCode == Activity.RESULT_FIRST_USER){
//                String nTitle = data.getStringExtra("EXISTING_NOTE_TITLE");
//                String nText = data.getStringExtra("EXISTING_NOTE_TEXT");
//                MNote updatedNote = new MNote(nTitle, nText);
//                Intent intent = new Intent(getApplicationContext(), NoteListActivity.class);
//                intent.putExtra("UPDATED_NOTE", updatedNote);
//                intent.putExtra("UPDATED_NOTE_POSITION", notesIndex);
//                setResult(Activity.RESULT_FIRST_USER, intent);
//            }
//            else {
//
//            }
//        }
//    }
}
