package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.startng.newsapp.database.Notes;
import com.startng.newsapp.database.NotesViewModel;
import com.startng.newsapp.utils.NotesAdapter;
import com.startng.newsapp.utils.NotesDecoration;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    NotesAdapter notesAdapter;
    View emptyView;
    RecyclerView recycle;
    NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyView = findViewById(R.id.empty_view);
        recycle = findViewById(R.id.note_recycler_view);
        StaggeredGridLayoutManager glm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        int spacing = 8;
        recycle.setPadding(spacing,spacing,spacing,spacing);
        recycle.setClipToPadding(false);
        recycle.setClipChildren(false);
        recycle.addItemDecoration(new NotesDecoration(spacing));
        recycle.setLayoutManager(glm);
        recycle.setHasFixedSize(true);

        notesAdapter = new NotesAdapter(this,new NotesAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int index) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("item_id", notesAdapter.getRoomItemId(index));
                startActivity(intent);
            }
        });
        recycle.setAdapter(notesAdapter);

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                if(notes != null) {
                    notesAdapter.setWords(notes);
                    if(!notes.isEmpty()){
                    emptyView.setVisibility(View.GONE);
                    }else{
                    emptyView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(i);
            }
        });

    }
}
