package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;

import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNoteActivity extends AppCompatActivity {
    private MaterialToolbar mToolbar ;
    private ActionMenuItemView menuItem;
    private TextInputEditText inputEditText, noteTitleEditText;

    DatabaseReference databaseNote;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note_activity);

        databaseNote = FirebaseDatabase.getInstance().getReference("Notes");

        mToolbar = (MaterialToolbar) findViewById(R.id.topAppBarAct);
        setSupportActionBar(mToolbar);

        menuItem = findViewById(R.id.favorite);

        inputEditText = findViewById(R.id.new_note);

        noteTitleEditText = findViewById(R.id.note_title);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_app_bar_second_activity,menu);
        return true;
    }



    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if ( itemId == R.id.favorite)
        {
            menuItem.setIcon(android.graphics.drawable.Drawable.createFromPath("C:\\Users\\user\\Desktop\\NotesApp\\app\\src\\main\\res\\drawable\\ic_favorite_24dp.png"));
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
            return true;
        }
        // check if the done item is clicked
        // if so , add new notes to database
        if(itemId == R.id.save){

            addNotes();
            Toast.makeText(this,"note added",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    // creating new notes.
    private void addNotes(){
        String title = noteTitleEditText.getText().toString().trim();
        String content= inputEditText.getText().toString().trim();
        // check if note title is empty
        if(!TextUtils.isEmpty(title))
        {
            // dont know what happens here yet
        }
        else{
            Toast.makeText(this,"add a title",Toast.LENGTH_LONG);
        }
        // checking if note edit text is empty
        if(!TextUtils.isEmpty(content)) {

              String  id = databaseNote.push().getKey();

              RecyclerItems items = new RecyclerItems(id, R.id.delete_icon, title, content);

                databaseNote.child(id).setValue(items);
        }
        else{
            Toast.makeText(this,"add a note",Toast.LENGTH_LONG).show();
        }
    }
}

