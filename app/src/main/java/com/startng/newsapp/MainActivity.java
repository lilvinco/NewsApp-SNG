package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String NOTE_TITLE = "note_title";
    public static final String NOTE_CONTENT = "note_content";
    private EditText editNoteTitle;
    private EditText editNoteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNoteTitle = findViewById(R.id.edit_note_title);
        editNoteContent = findViewById(R.id.edit_note_content);
    }

    @Override
    public void onBackPressed() {
        String noteTitle, noteContent;
        Intent resultIntent = new Intent();
        if (TextUtils.isEmpty(editNoteTitle.getText()) && TextUtils.isEmpty(editNoteContent.getText())) {
            finish();
        }
        else {
            noteTitle = editNoteTitle.getText().toString();
            noteContent = editNoteContent.getText().toString();
            resultIntent.putExtra(NOTE_TITLE, noteTitle);
            resultIntent.putExtra(NOTE_CONTENT, noteContent);
            setResult(RESULT_OK, resultIntent);
        }
        finish();
        super.onBackPressed();
    }
}
