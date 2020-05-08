package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.startng.newsapp.db.NoteDB;
import com.startng.newsapp.db.NoteDao;

import java.util.ArrayList;
import java.util.List;

import static com.startng.newsapp.EditNoteActivity.NOTE_EXTRA_Key;

public class MainActivity extends AppCompatActivity implements NoteEventListener {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ArrayList<HeadlinesActivity> notes;
    private HeadlinesAdapter adapter;
    private NoteDao dao;
    private MainActionMode actionMode;
    private int checkedCount = 0;
    private FloatingActionButton fab;
    private SharedPreferences settings;
    public static final String THEME_Key = "app_theme";
    public static final String APP_PREFERENCES = "notepad_settings";
    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        theme = settings.getInt(THEME_Key, R.style.AppTheme);
        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // init recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // init fab button
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 03/05/2020 add new note
                onAddNewHeadlineActivity();
            }
        });

        dao = NoteDB.getInstance(this).noteDao();
    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        List<HeadlinesActivity> list = dao.getNotes();

        this.notes.addAll(list);
        this.adapter = new HeadlinesAdapter(this, notes);

        this.adapter.setListener(this);

        this.recyclerView.setAdapter(adapter);
    }

    private void onAddNewHeadlineActivity() {
        startActivity(new Intent(this, EditNoteActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    public void onNoteClick(HeadlinesActivity note) {
        Intent edit = new Intent(this, EditNoteActivity.class);
        edit.putExtra(NOTE_EXTRA_Key, note.getId());
        startActivity(edit);
    }

    @Override
    public void onNoteLongClick(HeadlinesActivity note) {
        note.setChecked(true);
        checkedCount = 1;
        adapter.setMultiCheckMode(true);

        adapter.setListener(new NoteEventListener() {
            @Override
            public void onNoteClick(HeadlinesActivity note) {
                note.setChecked(!note.isChecked());
                if (note.isChecked())
                    checkedCount++;
                else checkedCount--;

                if (checkedCount > 1) {
                    actionMode.changeShareItemVisible(false);
                } else actionMode.changeShareItemVisible(true);

                if (checkedCount == 0) {
                    actionMode.getAction().finish();
                }


                actionMode.setCount(checkedCount + "/" + notes.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNoteLongClick(HeadlinesActivity note) {

            }
        });

        actionMode = new MainActionMode() {

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {


                if (menuItem.getItemId() == R.id.action_delete_notes)
                    onDeleteMultiNotes();
                else if (menuItem.getItemId() == R.id.action_share_note)
                    onShareNote();

                actionMode.finish();
                return false;
            }
        };

        startActionMode(actionMode);

        fab.setVisibility(View.GONE);
        actionMode.setCount(checkedCount + "/" + notes.size());

    }

    private void onShareNote () {

        HeadlinesActivity note = adapter.getCheckedNotes().get(0);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        String notetext = note.getNoteText() + "\n Create on :" +
                NoteUtils.dateFromLong(note.getNoteDate()) + "\n By :" +
                getString(R.string.app_name);
        share.putExtra(Intent.EXTRA_TEXT, notetext);
        startActivity(share);
    }

    private void onDeleteMultiNotes() {
        List<HeadlinesActivity> checkedNotes = adapter.getCheckedNotes();
        if (checkedNotes.size() != 0) {
            for (HeadlinesActivity note : checkedNotes) {
                dao.deleteNote(note);
            }
            loadNotes();
            Toast.makeText(this, checkedNotes.size() + "Note(s) Delete successfully !", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "No Note(s) selected", Toast.LENGTH_SHORT).show();
    }

        @Override
        public void onActionModeFinished (ActionMode mode){
            super.onActionModeFinished(mode);

            adapter.setMultiCheckMode(false);
            adapter.setListener(this);
            fab.setVisibility(View.VISIBLE);
        }
}
