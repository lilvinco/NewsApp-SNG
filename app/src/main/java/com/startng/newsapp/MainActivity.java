package com.startng.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.widget.TextView;



import java.util.Objects;

public class MainActivity extends AppCompatActivity {
public static final String EXTRA_REPLY = "com.startng.newsapp.REPLY";
    private EditText editText;
    private EditText editText2;

    private NoteDatabase noteDatabase;
    private Note note;
    private NoteViewModel mWordViewModel;
    Boolean update = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         editText = findViewById(R.id.titleText);
         editText2 = findViewById(R.id.contentText);
         Button button = findViewById(R.id.but_save);

         mWordViewModel = new ViewModelProvider(this).get((NoteViewModel.class));

         if ( (note = (Note) getIntent().getSerializableExtra("note")) !=null) {
             Objects.requireNonNull(getSupportActionBar()).setTitle("Update Note");
             button.setText("Edit");
             update= true;
             editText.setText(note.getTitle());
             editText2.setText(note.getContent());

         }
        button.setOnClickListener(v -> {
            Intent intent = new Intent();
            if (update){
                note.setContent(editText.getText().toString());
                note.setTitle(editText2.getText().toString());
                mWordViewModel.update(note);
                intent.putExtra(EXTRA_REPLY, note);
                setResult(RESULT_OK, intent);
            }else {
                note = new Note(editText.getText().toString(), editText2.getText().toString());
                mWordViewModel.insert(note);
                intent.putExtra(EXTRA_REPLY, note);
                setResult(RESULT_OK, intent);
            }
            finish();
        });
    }
}

