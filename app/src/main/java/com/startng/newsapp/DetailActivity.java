package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class DetailActivity extends AppCompatActivity {
    public final static String NOTE_TITLE_TAG = "title";
    public final static String NOTE_CONTENT_TAG = "body";

    private EditText titleEditText;
    private EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleEditText = findViewById(R.id.editTextNoteTitle);
        contentEditText = findViewById(R.id.editTextNoteContent);

        String title = "";
        String content = "";

        //setup back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra(NOTE_TITLE_TAG);
            content = intent.getStringExtra(NOTE_CONTENT_TAG);

            titleEditText.setText(title);
            contentEditText.setText(content);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            //respond to Save click
            case R.id.save:
                //TODO: Work on Save Click
                saveNote();
                break;
            //respond to Category click
            case R.id.category:
                //TODO: Work on Category Click
                break;
            //respond to Reminder click
            case R.id.reminder:
                //TODO: Work on Reminder Click
                break;
            //respond to Delete click
            case R.id.delete:
                //TODO: Work on Delete Click
                break;
            //respond to Share click
            case R.id.share:
                //TODO: Work on Share Click
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void saveNote(){
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        DataManager.addToDB(new Notes(title, content));
        WelcomeActivity.refreshView();
        finish();
    }
}
