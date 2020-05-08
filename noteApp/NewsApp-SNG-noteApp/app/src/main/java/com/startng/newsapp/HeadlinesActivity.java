package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.startng.newsapp.data.NotesContract.NotesEntry;

import java.util.List;

public class HeadlinesActivity extends AppCompatActivity  {
    public static final int  ADD_NOTE_REQUEST = 1;
    public static final int  EDIT_NOTE_REQUEST = 2;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    int number;
    private String[] myDataset =  {"Lagos","Abuja"};
    private NoteViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headlines);
        final HeadlinesAdapter adapter = new HeadlinesAdapter();

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        model = new NoteViewModel(this);
        model.getAllNotes().observe(this, new Observer<List<Notes>>() {
           @Override
           public void onChanged(List<Notes> notes) {
               adapter.submitList(notes);

           }
       });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HeadlinesActivity.this, MainActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            model.delete(adapter.getNoteAtPosition(viewHolder.getAdapterPosition()));
            Toast.makeText(getApplicationContext(),"Note deleted",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new HeadlinesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Notes notes) {
                Intent intent = new Intent(HeadlinesActivity.this,MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_ID,notes.getId());
                intent.putExtra(MainActivity.EXTRA_TITLE,notes.getTitle());
                intent.putExtra(MainActivity.EXTRA_DESCRIPTION,notes.getDescription());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(MainActivity.EXTRA_TITLE);
            String description = data.getStringExtra(MainActivity.EXTRA_DESCRIPTION);

            Notes note = new Notes(title,description);
            Toast toast = Toast.makeText(getApplicationContext(),"Note Saved",Toast.LENGTH_SHORT);
            model.insert(note);
            toast.show();
        }else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(MainActivity.EXTRA_ID,-1);
            if(id == -1){
                Toast.makeText(this,"Note can't be updated",Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(MainActivity.EXTRA_TITLE);
            String description = data.getStringExtra(MainActivity.EXTRA_DESCRIPTION);

            Notes note = new Notes(title,description);
            note.setId(id);
            model.update(note);

        }
        else {
            if(requestCode == ADD_NOTE_REQUEST){
                Toast.makeText(getApplicationContext(),"Note discarded", Toast.LENGTH_SHORT).show();
            }else if(requestCode == EDIT_NOTE_REQUEST){
                Toast.makeText(getApplicationContext(),"Change discarded",Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        // Respond to a click on the "Delete all entries" menu option
        if (item.getItemId() == R.id.action_delete_all_entries) {
            if (model.getAllNotes() != null){
                model.deleteAllNotes();
                Toast.makeText(getApplicationContext(),"All notes deleted",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }







}

