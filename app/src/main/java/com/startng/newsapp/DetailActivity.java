package com.startng.newsapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static com.startng.newsapp.NoteDBContract.NoteEntry;

public class DetailActivity extends AppCompatActivity {
    public final static String NOTE_ID_TAG = "NOTE_ID";

    private EditText titleEditText;
    private EditText contentEditText;

    private NoteDBHelper databaseHelper;
    private SQLiteDatabase database;
    private int notedID;

    private String title;
    private String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleEditText = findViewById(R.id.editTextNoteTitle);
        contentEditText = findViewById(R.id.editTextNoteContent);
        //setup back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent != null) {
            int defaultValue = -1;
            notedID = getIntent().getIntExtra(NOTE_ID_TAG, defaultValue);
            if (notedID != defaultValue) {
                MyNotes myNotes = new MyNotes();
                databaseHelper = new NoteDBHelper(this);
                myNotes = DataManager.fetchNote(databaseHelper, notedID);

                titleEditText.setText(myNotes.getNoteTitle());
                contentEditText.setText(myNotes.getNoteContent());
            }
        }

        title = titleEditText.getText().toString();
        content = contentEditText.getText().toString();
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
                saveNote();
                break;
            //respond to Category click
            case R.id.category:
                //TODO: Work on Category Click
                break;
            //respond to Reminder click
            case R.id.reminder:
                //TODO: Work on Reminder Click
                startActivity(new Intent(this, AlarmActivity.class));
                break;
            //respond to Delete click
            case R.id.delete:
                deleteNote();
                break;
            //respond to Share click
            case R.id.share:
                shareNotes();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirm_sure)
                .setTitle(R.string.delete_note)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataManager.deleteNote(databaseHelper, notedID);
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void saveNote() {
        title = titleEditText.getText().toString();
        content = contentEditText.getText().toString();

        if (title.length() != 0) {
            databaseHelper = new NoteDBHelper(this);
            database = databaseHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(NoteEntry.COLUMN_CONTENT, content);
            contentValues.put(NoteEntry.COLUMN_TITLE, title);

            long insert = database.insert(NoteEntry.TABLE_NAME, null, contentValues);
            if (insert > 0) {
                Toast.makeText(getApplicationContext(), "Added to DB :" + insert, Toast.LENGTH_SHORT).show();
            }
            finish();
        } else {
            titleEditText.setError("Required Field");
        }
    }

    private void shareNotes(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, content);

        Intent chooser = Intent.createChooser(intent,"Share your note with...");
        // Verify that the intent will resolve to an activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

}
