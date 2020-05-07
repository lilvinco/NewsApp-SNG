package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.UUID;

public class NoteListActivity extends AppCompatActivity implements NoteRecyclerAdapter.OnDeleteClickListener {

    private static final int CREATE_REQUEST_CODE = 1;
    public static final int UPDATE_REQUEST_CODE = 2;
    private String TAG = this.getClass().getSimpleName();
    private NoteViewModel noteViewModel;
    private NoteRecyclerAdapter noteRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_notes);
        noteRecyclerAdapter = new NoteRecyclerAdapter(this, this);
        recyclerView.setAdapter(noteRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(NoteListActivity.this, MainActivity.class), CREATE_REQUEST_CODE);
            }
        });

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getListOfNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteRecyclerAdapter.setNotes(notes);

                TextView textInfo = findViewById(R.id.text_info);

                if (noteRecyclerAdapter.getItemCount() < 1) {
                    textInfo.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else {
                    textInfo.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(0, data.getStringExtra(MainActivity.NOTE_TITLE), data.getStringExtra(MainActivity.NOTE_CONTENT));
            noteViewModel.insert(note);

            Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == UPDATE_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(data.getIntExtra(EditActivity.NOTE_ID, 0), data.getStringExtra(EditActivity.NOTE_TITLE), data.getStringExtra(EditActivity.NOTE_CONTENT));
            noteViewModel.update(note);

            Toast.makeText(getApplicationContext(), "Note updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete_all) {
            confirmDelete();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete all notes?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        noteViewModel.deleteAll();
                        Toast.makeText(getApplicationContext(), "All Notes delete", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setTitle("Confirm");
        dialog.show();
    }

    @Override
    public void OnDeleteClickListener(Note myNote) {
        noteViewModel.delete(myNote);
    }
}

