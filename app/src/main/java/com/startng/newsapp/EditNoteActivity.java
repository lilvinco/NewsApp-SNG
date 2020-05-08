package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.startng.newsapp.db.NoteDB;
import com.startng.newsapp.db.NoteDao;

import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {
    private EditText inputNote;
    private NoteDao dao;
    private HeadlinesActivity temp;
    public static final String NOTE_EXTRA_Key = "note_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        int theme = sharedPreferences.getInt(MainActivity.THEME_Key, R.style.AppTheme);
        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        inputNote = findViewById(R.id.input_note);
        dao = NoteDB.getInstance(this).noteDao();

        if (getIntent().getExtras()!=null) {
            int id = getIntent().getExtras().getInt(NOTE_EXTRA_Key,  0);
            temp = dao.getNoteById(id);
            inputNote.setText(temp.getNoteText());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_note)
            onSaveNote();
        return super.onOptionsItemSelected(item);
    }

    private void onSaveNote() {
        String text = inputNote.getText().toString();
        if (!text.isEmpty()) {
            long date = new Date().getTime();

            if (temp == null) {
                temp = new HeadlinesActivity(text, date);
                dao.insertNote(temp);
            } else {
                temp.setNoteText(text);
                temp.setNoteDate(date);
                dao.updateNote(temp);
            }
            finish();
        }
    }
}
