package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
public static final String EXTRA_REPLY = "com.startng.newsapp.REPLY";

    EditText editText1;
    EditText editText2;

    NoteBook noteBook1;
    NotesAppDB notesAppDB1;
    private NotesViewModel mWordViewModel;
    Boolean update = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find editTextViews
        editText1 = findViewById(R.id.textView3);
        editText2 = findViewById(R.id.textView4);
        Button button = findViewById(R.id.button1);
        button.setText("Save");

        mWordViewModel = new ViewModelProvider(this).get((NotesViewModel.class));

        if ( (noteBook1 = (NoteBook) getIntent().getSerializableExtra("stuff")) != null) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(noteBook1.getTitle());
            button.setText("Update");
            update = true;
            editText1.setText(noteBook1.getTitle());
            editText2.setText(noteBook1.getContent());
        }

        button.setOnClickListener(v -> {
            Intent intent = new Intent();
            if (update){
                noteBook1.setContent(editText2.getText().toString());
                noteBook1.setTitles(editText1.getText().toString());
                mWordViewModel.update(noteBook1);
                intent.putExtra(EXTRA_REPLY, noteBook1);
                setResult(RESULT_OK, intent);
            }else {
                noteBook1 = new NoteBook(editText1.getText().toString(), editText2.getText().toString());
                mWordViewModel.insert(noteBook1);
                intent.putExtra(EXTRA_REPLY, noteBook1);
                setResult(RESULT_OK, intent);
            }
            finish();
        });
    }
}
