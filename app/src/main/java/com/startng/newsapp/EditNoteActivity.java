package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.startng.newsapp.db.NotesDB;
import com.startng.newsapp.db.NotesDao;

import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {

    EditText input_title, input_note;
    FloatingActionButton fab_back;

    private NotesDao mDao;
    private Note temp;
    public static final String NOTE_EXTRA_KEY = "note_extra_id";
    public static final String NOTE_EDIT_KEY = "note_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        input_title = findViewById(R.id.input_title);
        input_note = findViewById(R.id.input_note);
        fab_back = findViewById(R.id.fab_back);

        fab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDao = NotesDB.getInstance(this).mNotesDao();

        if (getIntent().getExtras() != null){
            int id = getIntent().getExtras().getInt(NOTE_EDIT_KEY, 0);
            temp = mDao.getNoteById(id);
            input_title.setText(temp.getNoteTitle());
            input_note.setText(temp.getNoteText());
        } else
            temp = new Note();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.save_note) {
            onSaveNote();
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSaveNote() {
        String title = input_title.getText().toString();
        String text = input_note.getText().toString();

        if (!text.isEmpty() || !title.isEmpty()) {
            long date = new Date().getTime();

            //if note exist update else create new note
            temp.setNoteTitle(title);
            temp.setNoteText(text);
            temp.setNoteDate(date);

            if(temp.getId() != getTaskId()){
                //Note note = new Note(text, date); //create new Note
                mDao.insertNote(temp); //insert and save note to database
            } else
                mDao.updateNote(temp);

        } else
            Toast.makeText(this, "Empty note not saved!", Toast.LENGTH_SHORT).show();

        finish(); //return to NoteActivity

    }
}


