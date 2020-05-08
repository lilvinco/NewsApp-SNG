package com.startng.newsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

public class UpdateNote extends AppCompatActivity {

    private MaterialToolbar mToolbar ;
    private ActionMenuItemView menuItem;
    private TextInputEditText inputEditText, noteTitleEditText;



    private  String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);



        mToolbar = (MaterialToolbar) findViewById(R.id.topAppBarAct);
        setSupportActionBar(mToolbar);

        menuItem = findViewById(R.id.favorite);

        inputEditText = findViewById(R.id.new_note);

        noteTitleEditText = findViewById(R.id.note_title);

        Intent intent = getIntent();

       id= intent.getStringExtra(MainActivity.Note_Id);
       String title = intent.getStringExtra(MainActivity.Note_Title);
       String content = intent.getStringExtra(MainActivity.Note_content);

       noteTitleEditText.setText(title);
       inputEditText.setText(content);
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

            updateNotes();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateNotes()
    {
        String title = noteTitleEditText.getText().toString().trim();
        String content = inputEditText.getText().toString().trim();

        if((TextUtils.isEmpty(title) && (TextUtils.isEmpty(content))))
        {
            noteTitleEditText.setError("title required");
            inputEditText.setError(" note required ");
            return;
        }
        else {
        DatabaseReference databaseUpdate = FirebaseDatabase.getInstance().getReference("Notes").child(id);

        RecyclerItems items = new RecyclerItems(id, R.id.delete_icon, title, content);

        databaseUpdate.setValue(items);

        Toast.makeText(this, "note updated", Toast.LENGTH_LONG).show();
    }
    }

}
