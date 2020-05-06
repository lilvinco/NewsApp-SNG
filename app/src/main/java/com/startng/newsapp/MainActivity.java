package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.android.material.textfield.TextInputEditText;
import com.startng.newsapp.model.MyDatabase;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private Note note;
    private boolean update;
    private MyDatabase noteDatabase;
    private TextInputEditText heading, body;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        //Find textviews, get the extras and assign both to each other
        heading = findViewById(R.id.heading);
        body = findViewById(R.id.body);
        noteDatabase = MyDatabase.getInstance(MainActivity.this);
        Button button = findViewById(R.id.save);
        if((note = (Note) getIntent().getSerializableExtra("note")) != null){
            getSupportActionBar().setTitle("Update Note");
            update = true;
            button.setText("Update");
            heading.setText(note.getHeading());
            body.setText(note.getBody());
        }
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(update){
                    note.setBody(body.getText().toString());
                    note.setHeading(heading.getText().toString());
                    noteDatabase.getNoteDao().updateNote(note);
                    setResult(note, 2);

                }else {
                    note = new Note(body.getText().toString(),heading.getText().toString());
                    new InsertTask(MainActivity.this, note).execute();
                }

            }
        });

    }
    private void setResult(Note note, int flag) {
        setResult(flag, new Intent().putExtra("note", note));
        finish();
    }
    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<MainActivity> activityReference;
        private Note note;

        // only retain a weak reference to the activity
        InsertTask(MainActivity context, Note note) {
            activityReference = new WeakReference<>(context);
            this.note = note;
        }

        @Override
        protected Boolean doInBackground(Void... objs) {
            long j = activityReference.get().noteDatabase.getNoteDao().insertNote(note);
            note.setId(j);
            Log.e("ID ", "doInBackground: " + j);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                activityReference.get().setResult(note, 1);
                activityReference.get().finish();
            }
        }
    }
}
