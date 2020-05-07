package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.delete_note){
            new AlertDialog.Builder(NoteEditorActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this note?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NoteListActivity.notes.remove(noteId);
                            NoteListActivity.mAdapter.notifyDataSetChanged();

                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.startng.newsapp", Context.MODE_PRIVATE);

                            HashSet<String> set = new HashSet<>(NoteListActivity.notes);

                            sharedPreferences.edit().putStringSet("notes", set).apply();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        //Find textviews, get the extras and assign both to each other
        EditText editText = findViewById(R.id.editText);
        noteId = getIntent().getIntExtra("noteId", -1);

        if(noteId != -1){
            editText.setText(NoteListActivity.notes.get(noteId));
        } else{
            NoteListActivity.notes.add("");
            noteId = NoteListActivity.notes.size() - 1;
            NoteListActivity.mAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NoteListActivity.notes.set(noteId, String.valueOf(charSequence));
                NoteListActivity.mAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.startng.newsapp", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<>(NoteListActivity.notes);

                sharedPreferences.edit().putStringSet("notes", set).apply();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
