package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;

public class NoteListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoteListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Note> notes;
    private ImageView btn_add;


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

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void loadNote() {
        this.notes = new ArrayList<>();
        for (int i = 0; i<12; i++) {
            notes.add(new Note("This is a note application that allow users create,edit ,view and delete note. this app\n" +
                    "             must be submitted today",
                    new Date().getTime()));
        }
        adapter = new NoteListAdapter(notes,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void onAddNewNote() {
        if (notes != null)
            notes.add(new Note("This is a new note", new Date().getTime()));
        if (adapter!= null)
            adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNote();
    }

}

