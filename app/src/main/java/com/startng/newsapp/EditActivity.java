package com.startng.newsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class EditActivity extends AppCompatActivity {

    public static final String NOTE_ID = "note_id";
    public static final String NOTE_TITLE = "note_title";
    public static final String NOTE_CONTENT = "note_content";
    private EditText editNoteTitle;
    private EditText editNoteContent;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.edit_note);

        editNoteTitle = findViewById(R.id.edit_note_title);
        editNoteContent = findViewById(R.id.edit_note_content);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            noteId = bundle.getInt(NOTE_ID);
        }

        NoteViewModel noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        LiveData<Note> noteLiveData = noteViewModel.getNote(noteId);
        noteLiveData.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                editNoteTitle.setText(note.getNoteTitle());
                editNoteContent.setText(note.getNoteContent());
            }
        });
    }

    @Override
    public void onBackPressed() {
        String noteTitle, noteContent;
        Intent resultIntent = new Intent();
        if (TextUtils.isEmpty(editNoteTitle.getText()) && TextUtils.isEmpty(editNoteContent.getText())) {
            setResult(RESULT_CANCELED, resultIntent);
        }
        else {
            noteTitle = editNoteTitle.getText().toString();
            noteContent = editNoteContent.getText().toString();
            resultIntent.putExtra(NOTE_ID, noteId);
            resultIntent.putExtra(NOTE_TITLE, noteTitle);
            resultIntent.putExtra(NOTE_CONTENT, noteContent);
            setResult(RESULT_OK, resultIntent);
        }
        finish();
        super.onBackPressed();
    }
}
