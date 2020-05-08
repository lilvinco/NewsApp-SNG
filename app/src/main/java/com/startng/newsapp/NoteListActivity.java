package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.startng.newsapp.db.NotesDB;
import com.startng.newsapp.db.NotesDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
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

}

