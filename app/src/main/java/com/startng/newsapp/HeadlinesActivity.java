package com.startng.newsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.startng.newsapp.callbacks.NoteEventListener;
import com.startng.newsapp.db.NotesDB;
import com.startng.newsapp.db.NotesDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.startng.newsapp.EditNoteActivity.NOTE_EDIT_KEY;
import static com.startng.newsapp.EditNoteActivity.NOTE_EXTRA_KEY;

public class HeadlinesActivity extends AppCompatActivity implements NoteEventListener{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private NotesDao mDao;
    ArrayList<Note> mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headlines);

        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNewNote();
            }
        });

        mDao = NotesDB.getInstance(this).mNotesDao();

    }

    private void loadNotes() {
        // specify an adapter (see also next example)
        //String[] myDataset = getResources().getStringArray(R.array.sports_info);

        this.mNotes = new ArrayList<>();
        List<Note> list = mDao.getNotes(); //gets all the notes from the Database
        this.mNotes.addAll(list);
        this.mAdapter = new HeadlinesAdapter(this, mNotes);

        ((HeadlinesAdapter) this.mAdapter).setListener((NoteEventListener) this);
        recyclerView.setAdapter(mAdapter);
    }

    private void onAddNewNote() {
        startActivity(new Intent(this, EditNoteActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    public void onNoteClick(Note note) {
        Intent editIntent = new Intent(HeadlinesActivity.this, EditNoteActivity.class);
        editIntent.putExtra(NOTE_EDIT_KEY, note.getId());
        startActivity(editIntent);
    }


    @Override
    public void onNoteLongClick(final Note note) {

        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // delete note and refresh from database
                        mDao.deleteNote(note); // delete notes
                        loadNotes(); // refreshes notes database

                    }
                })
                .setNegativeButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //share note and refresh from database
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        String shareText = getString(R.string.share_title_bold) + note.getNoteTitle() + "\n" + note.getNoteText() + "\nCreated on: " + NoteUtils.dateFromLong(note.getNoteDate());
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                        shareIntent.setType("text/plain");
                        startActivity(Intent.createChooser(shareIntent, null));
                    }
                })
                .create()
                .show();

    }

}

