package com.startng.newsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HeadlinesActivity extends AppCompatActivity implements NoteIntent {

    private NoteViewModel mWordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headlines);

        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // specify an adapter (see also next example)
        HeadlinesAdapter mAdapter = new HeadlinesAdapter(this, this);
        recyclerView.setAdapter(mAdapter);

        mWordViewModel = new ViewModelProvider(this).get((NoteViewModel.class));
        // Update the cached copy of the words in the adapter.
        mWordViewModel.getAllWords().observe(this, mAdapter::setNoteBook);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(HeadlinesActivity.this, MainActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }

    /* public void addNumber(View view) {
        number++;
        TextView textView = findViewById(R.id.numbertextView);
        textView.setText(String.valueOf(number));
    }*/
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode != RESULT_OK) {
            Toast.makeText(
                    getApplicationContext(),
                    "Empty: Not Saved",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void updateIntent(Note notes) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("note", notes);
        startActivity(intent);
    }

    @Override
    public void deleteIntent(Note notes) {
        mWordViewModel.delete(notes);
    }
}

