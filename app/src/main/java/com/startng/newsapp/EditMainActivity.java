package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

public class EditMainActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.startng.newsapp.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.startng.newsapp.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.startng.newsapp.EXTRA_DESCRIPTION";

    private EditText editTextTitle;
    private EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Find the textViews
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);


        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra((EXTRA_TITLE)));
            editTextDescription.setText(intent.getStringExtra((EXTRA_DESCRIPTION)));
        } else {
            setTitle("Create Note");
        }
    }


    private void saveNote() {
        //Get the extras and assign both to each other
        String title = editTextTitle.getText().toString();
        String note = editTextDescription.getText().toString();

        if (title.trim().isEmpty() || note.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and note", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, note);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        //puts id into the intent if its not -1
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        //data is passed and that activity ends
        setResult(RESULT_OK, data);
        finish();
    }

    //Initializes the content of the menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    //For the save button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
