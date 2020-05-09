package com.startng.newsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Details extends AppCompatActivity {
    TextView mDetails;

    private static final String TAG = Details.class.getSimpleName();

    String title, time, date, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDetails = findViewById(R.id.detailsOfNote);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            title = bundle.getString("title");
            time = bundle.getString("time");
            date = bundle.getString("date");
            content = bundle.getString("content");
        }

        assert title != null;
        Log.d(TAG, title);
        Log.d(TAG, time);
        Log.d(TAG, date);
        Log.d(TAG, content);

        mDetails.setText(content);
        getSupportActionBar().setTitle(title);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Details.this, "Delete btn is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.editNote){
            // send user to edit activity
            Toast.makeText(this, "Edit Note", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
