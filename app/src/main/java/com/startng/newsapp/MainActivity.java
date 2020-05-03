package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE ="com.startng.newsapp.EXTRA_TITLE" ;
    private EditText mEditNoteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mEditNoteView = findViewById(R.id.EdAdd);
        setTitle("Add Note");
        //String headline = getIntent().getStringExtra("headline");
        //textView.setText(headline);
    }
    private void saveNote(){
        String string = mEditNoteView.getText().toString();

        if (string.trim().isEmpty()){
            Toast.makeText(this,"empty note", Toast.LENGTH_SHORT);
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, string);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.done_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Intent replyIntent = new Intent();
        switch (item.getItemId()){
            case R.id.done:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
