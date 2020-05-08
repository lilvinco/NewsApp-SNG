package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.startng.newsapp.callbacks.NoteEventListener;

import static com.startng.newsapp.EditNoteActivity.NOTE_EDIT_KEY;
import static com.startng.newsapp.EditNoteActivity.NOTE_EXTRA_KEY;

public class MainActivity extends AppCompatActivity implements NoteEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find textviews, get the extras and assign both to each other
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvText = findViewById(R.id.tvText);

        String headline = getIntent().getStringExtra("title");
        tvTitle.setText(headline);

        String text = getIntent().getStringExtra("headline");
        tvText.setText(text);


    }

    @Override
    public void onNoteClick(Note note) {
//        Intent editIntent = new Intent(SecondActivity.this, EditNoteActivity.class);
//        editIntent.putExtra(NOTE_EDIT_KEY, note.getId());
//        startActivity(editIntent);
    }

    @Override
    public void onNoteLongClick(Note note) {

    }
}
