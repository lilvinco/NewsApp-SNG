package com.startng.newsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.startng.newsapp.model.MyDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AllNotesActivity extends AppCompatActivity implements HeadlinesAdapter.OnNoteItemClick {

    private TextView textViewMsg;
    private MyDatabase noteDatabase;
    private List<Note> notes;
    private HeadlinesAdapter notesAdapter;
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVies();
        displayList();
    }

    private void displayList() {
        noteDatabase = MyDatabase.getInstance(AllNotesActivity.this);
        new RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void, Void, List<Note>> {

        private WeakReference<AllNotesActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(AllNotesActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Note> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().noteDatabase.getNoteDao().getNotes();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            if (notes != null && notes.size() > 0) {
                activityReference.get().notes.clear();
                activityReference.get().notes.addAll(notes);
                // hides empty text view
                activityReference.get().textViewMsg.setVisibility(View.GONE);
                activityReference.get().notesAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initializeVies() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewMsg = (TextView) findViewById(R.id.tv__empty);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(listener);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllNotesActivity.this));
        notes = new ArrayList<>();
        notesAdapter = new HeadlinesAdapter(notes, AllNotesActivity.this);
        recyclerView.setAdapter(notesAdapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(AllNotesActivity.this, MainActivity.class), 100);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            if (resultCode == 1) {
                notes.add((Note) data.getSerializableExtra("note"));
            } else if (resultCode == 2) {
                notes.set(pos, (Note) data.getSerializableExtra("note"));
            }
            listVisibility();
        }
    }

    @Override
    public void onNoteClick(final int pos) {
        new AlertDialog.Builder(AllNotesActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete Note", "Edit Note"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                noteDatabase.getNoteDao().deleteNote(notes.get(pos));
                                notes.remove(pos);
                                listVisibility();
                                break;
                            case 1:
                                AllNotesActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(AllNotesActivity.this,
                                                MainActivity.class).putExtra("note", notes.get(pos)),
                                        100);

                                break;
                        }
                    }
                }).show();

    }

    private void listVisibility() {
        int emptyMsgVisibility = View.GONE;
        if (notes.size() == 0) { // no item to display
            if (textViewMsg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        textViewMsg.setVisibility(emptyMsgVisibility);
        notesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        noteDatabase.cleanUp();
        super.onDestroy();
    }
}
