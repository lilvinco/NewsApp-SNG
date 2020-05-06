package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class NoteListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

//        recyclerView = findViewById(R.id.recycler_notes);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        String[] myDataset = getResources().getStringArray(R.array.sports_info);
//        mAdapter = new NoteRecyclerAdapter(this, myDataset);
//        recyclerView.setAdapter(mAdapter);

    }

}

