package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class HeadlinesActivity extends AppCompatActivity implements UpdateInteraction {
    private RecyclerView recyclerView;
    private NotesViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headlines);

        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // specify an adapter (see also next example)
        HeadlinesAdapter mAdapter = new HeadlinesAdapter(this, this);

        mWordViewModel = new ViewModelProvider(this).get((NotesViewModel.class));

        // Update the cached copy of the words in the adapter.
        mWordViewModel.getNoteBooks().observe(this, noteBooks -> {

            TextView newText = findViewById(R.id.pretext);

            if(noteBooks.isEmpty()) {
                newText.setVisibility(View.VISIBLE);
            } else
                newText.setVisibility(View.GONE);

            mAdapter.setNoteBook(noteBooks);
        });
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(HeadlinesActivity.this, MainActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(viewHolder.itemView.getContext()).setTitle("Delete").setMessage("Proceed to Delete?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            mWordViewModel.delete(mAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                            dialog.dismiss();
                        }).setNegativeButton("No", (dialog, which) -> {
                            mAdapter.notifyDataSetChanged();
                            dialog.cancel();
                })
                        .create().show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    /* public void addNumber(View view) {
        number++;
        TextView textView = findViewById(R.id.numbertextView);
        textView.setText(String.valueOf(number));
    }*/
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode != RESULT_OK) {
            Toast.makeText(
                    getApplicationContext(),
                    "Empty: Not Saved",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNewActivity(NoteBook noteBookList) {
        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.putExtra("stuff", (Serializable) noteBookList);
        startActivity(intent1);
    }

    @Override
    public void deleteRow(NoteBook deleteNote) {
        mWordViewModel.delete(deleteNote);
    }

    @Override
    public void deleteRow2(NoteBook... deleteNote) {
        mWordViewModel.delete2(deleteNote);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.delete_all_options, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.delete_all:
                new AlertDialog.Builder(this).setTitle("Delete All").setMessage("Proceed to delete everything?")
                        .setPositiveButton("Yes", ((dialog, which) -> {
                            mWordViewModel.clearTable();
                            dialog.dismiss();
                        })
                        ).setNegativeButton("No", (dialog, which) -> dialog.cancel()).create().show();
        }
        switch (item.getItemId()){
            case R.id.add_note:
                Intent intent = new Intent(HeadlinesActivity.this, MainActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }
}
