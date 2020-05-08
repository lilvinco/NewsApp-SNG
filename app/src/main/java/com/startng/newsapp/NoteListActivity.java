package com.startng.newsapp;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.startng.newsapp.db.NotesDB;
import com.startng.newsapp.db.NotesDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.startng.newsapp.MainActivity.NOTE_EXTRA_Key;

public class NoteListActivity extends AppCompatActivity implements NoteEventListener {

    private static final String TAG = "NoteListActivity";
    private RecyclerView recyclerView;
    private NoteListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Note> notes;
    private ImageView btn_add;
    private NotesDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelists);

        btn_add = findViewById(R.id.image_action_add);
        recyclerView = findViewById(R.id.my_recycler_view);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNewNote();

            }
        });

        dao = NotesDB.getInstance(this).notesDao();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void loadNote() {
        this.notes = new ArrayList<>();
        List<Note> list = dao.getNotes();
        this.notes.addAll(list);
        this.adapter = new NoteListAdapter(notes,this);

        //set listener to adapter

        this.adapter.setListener(this);
        this.recyclerView.setAdapter(adapter);
       // adapter.notifyDataSetChanged();
    }

    private void onAddNewNote() {
     startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNote();
    }

    @Override
    public void onNoteClick(Note note) {
        Intent editNote = new Intent(this, MainActivity.class);
        editNote.putExtra(NOTE_EXTRA_Key, note.getId());
        startActivity(editNote);

    }

    @Override
    public void onNoteLongClick(final Note note) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.main_app)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dao.deleteNote(note);
                        loadNote();

                    }
                })
                .setNegativeButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent share = new Intent(Intent.ACTION_SEND);
                        String text = note.getNoteText() + "\n Created on:" +
                                NoteUtils.dateFromLong(note.getNoteDate());
                        String subject = getString(R.string.app_name) +"content";

                        share.setType("text/plain");
                        share.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                        share.putExtra(android.content.Intent.EXTRA_TEXT, text);
                        startActivity(Intent.createChooser(share, "Share via"));

                    }
                })

                .create()
                .show();

    }
}

