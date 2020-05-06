package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HeadlinesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private NoteViewModel mNoteViewModel;
    int number;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headlines);


        recyclerView = findViewById(R.id.my_recycler_view);


        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //String[] mDataset = getResources().getStringArray(R.array.sports_info);
        final HeadlinesAdapter mAdapter = new HeadlinesAdapter();
        recyclerView.setAdapter(mAdapter);
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        mNoteViewModel.getAllWords().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable final List<Note> notes) {

               mAdapter.submitList(notes);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mNoteViewModel.delete(mAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(HeadlinesActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();

            }

        }).attachToRecyclerView(recyclerView);

        mAdapter.setOnItemClickListener(new HeadlinesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(HeadlinesActivity.this, EditMainActivity.class);
                intent.putExtra(EditMainActivity.EXTRA_ID, note.getId());
                intent.putExtra(EditMainActivity.EXTRA_TITLE, note.getNote());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HeadlinesActivity.this, EditMainActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            Note note = new Note(data.getStringExtra(EditMainActivity.EXTRA_TITLE));
            mNoteViewModel.insert(note);

            Toast.makeText(this,"Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditMainActivity.EXTRA_ID, -1);

            if (id == -1){
                Toast.makeText(this,"Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            Note note = new Note(data.getStringExtra(EditMainActivity.EXTRA_TITLE));
            note.setId(id);
            mNoteViewModel.update(note);

            Toast.makeText(this,"Note updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.more_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete:
                mNoteViewModel.deleteAll(note);
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }



}

