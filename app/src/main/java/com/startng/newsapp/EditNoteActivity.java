package com.startng.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EditNoteActivity extends AppCompatActivity {
    private EditText inputNoteTitle;
    private EditText inputNoteText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note);
        //connect layout edit texts to this java class
        inputNoteTitle = findViewById(R.id.input_note_title);
        inputNoteText = findViewById(R.id.input_note_text);

//        Intent intent = getIntent();
//        if(intent != null){
//            MNote note = intent.getParcelableExtra("NOTE_TO_EDIT");
//            String nTitle = note.getNoteTitle();
//            String nText = note.getNoteText();
//            inputNoteTitle.setText(nTitle);
//            inputNoteText.setText(nText);
//            editNote();
//        }
    }

//    public void editNote() {
//        String userNoteTitle = inputNoteTitle.getText().toString();
//        String userNoteText = inputNoteText.getText().toString();
//        final Intent intent = new Intent();
//        intent.putExtra("EXISTING_NOTE_TITLE", userNoteTitle);
//        intent.putExtra("EXISTING_NOTE_TEXT", userNoteText);
//        setResult(Activity.RESULT_FIRST_USER, intent);
//        //with finish() we close the editnote activity and return to the parent or caller
//        finish();
//    }

    public void returnToNoteList(View view) {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    public void createNewEmptyNote(View view) {
        String userNoteTitle = inputNoteTitle.getText().toString();
        String userNoteText = inputNoteText.getText().toString();
        final Intent intent = new Intent();
        intent.putExtra("NEW_NOTE_TITLE", userNoteTitle);
        intent.putExtra("NEW_NOTE_TEXT", userNoteText);
        setResult(Activity.RESULT_OK, intent);
        //with finish() we close the editnote activity and return to the parent or caller
        finish();
    }
}
