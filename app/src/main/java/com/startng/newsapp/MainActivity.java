package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.startng.newsapp.EXTRA_TITLE";
    public static final String EXTRA_CONTENT =
            "com.startng.newsapp.EXTRA_CONTENT";
    public static final String EXTRA_ID=
            "com.startng.newsapp.EXTRA_ID";

    private EditText editTitle, editContent;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find textviews, get the extras and assign both to each other
        editTitle = findViewById(R.id.edit_note_title);
        editContent = findViewById(R.id.edit_note_content);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editContent.setText(intent.getStringExtra(EXTRA_CONTENT));
            editTitle.setText(intent.getStringExtra(EXTRA_TITLE));
        } else {
            setTitle("Add Note");
        }
    }



    public void buttonClickHandler(View view){
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();

        if (title.trim().isEmpty() || content.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a Your note title and note", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_CONTENT, content);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.share) {
            shareNote();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void shareNote() {
        String subject = editTitle.getText().toString();
        String text = editContent.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);
    }
}
