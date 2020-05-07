package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import user_register.LoginActivity;

import static android.app.PendingIntent.getActivity;


public class NewNoteActivity extends AppCompatActivity {
    private Button newNote_add;
    private EditText newNote_title, newNote_desc;
    private Toolbar mToolBar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Menu mainMenu;
    private String noteID ;

    private boolean exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        try{
            noteID = getIntent().getStringExtra("noteId");

            if(!noteID.trim().equals("")) {
                exist = true;
            } else {
                exist = false;
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        newNote_add = (Button)findViewById(R.id.new_note_addButton);
        newNote_title = (EditText)findViewById(R.id.new_note_title);
        newNote_desc = (EditText)findViewById(R.id.new_note_desc);

        mToolBar = (Toolbar)findViewById(R.id.new_note_toolbar);

        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notes").child(firebaseAuth.getCurrentUser().getUid());


        newNote_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = newNote_title.getText().toString().trim();
                String desc = newNote_desc.getText().toString().trim();

                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc)){

                    createNote(title, desc);
                } else{
                    Snackbar.make(v, "Fill empty fields", Snackbar.LENGTH_SHORT).show();

                }

            }
        });
        putData();
    }

    private void putData(){
        if(exist) {

            databaseReference.child(noteID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("title") && dataSnapshot.hasChild("desc")) {
                        String title = dataSnapshot.child("title").getValue().toString();
                        String desc = dataSnapshot.child("desc").getValue().toString();

                        newNote_title.setText(title);
                        newNote_desc.setText(desc);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }





    }

    private void createNote(String title, String desc){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding Note, Please wait");
        progressDialog.show();

        if(firebaseAuth.getCurrentUser() != null){

            if(exist){
                Map updateMap = new HashMap();
                updateMap.put("title", newNote_title.getText().toString().trim());
                updateMap.put("desc",newNote_desc.getText().toString().trim());
                updateMap.put("timestamp", ServerValue.TIMESTAMP);
                databaseReference.child(noteID).updateChildren(updateMap);
                progressDialog.dismiss();
                Toast.makeText(NewNoteActivity.this, "Note Updated", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(NewNoteActivity.this, MainActivity.class);
                startActivity(mainIntent);

            } else {

                final DatabaseReference newNoteReference = databaseReference.push();
                final Map note_map = new HashMap();

                note_map.put("title", title);
                note_map.put("desc", desc);
                note_map.put("timestamp", ServerValue.TIMESTAMP);

                Thread mainThread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        newNoteReference.setValue(note_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(NewNoteActivity.this, "Note Added to Database", Toast.LENGTH_LONG).show();
                                    Intent mainIntent = new Intent(NewNoteActivity.this, MainActivity.class);
                                    startActivity(mainIntent);


                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(NewNoteActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    Intent mainIntent = new Intent(NewNoteActivity.this, MainActivity.class);
                                    startActivity(mainIntent);

                                }
                            }
                        });

                    }
                });

                mainThread.start();
            }
        } else{
            progressDialog.dismiss();
            Toast.makeText(this, "USER IS NOT SIGNED IN", Toast.LENGTH_LONG).show();
            Intent mainIntent = new Intent(NewNoteActivity.this, Start_Activity.class);
            startActivity(mainIntent);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.new_note_menu, menu);

        mainMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                break;
//                return true;

            case R.id.note_delete_btn:
                if(exist){
                    deleteNote();
                }
                else{
                    Toast.makeText(NewNoteActivity.this, "Nothing to delete", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(NewNoteActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }

                break;

        }
        return true;
//        return super.onOptionsItemSelected(item);
    }

    private void deleteNote(){
        databaseReference.child(noteID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(NewNoteActivity.this, "Note Deleted", Toast.LENGTH_LONG).show();
                    noteID = "no";
                    finish();
                    Intent mainIntent = new Intent(NewNoteActivity.this, MainActivity.class);
                    startActivity(mainIntent);

                } else {
                    Log.e("NewNoteActivity", task.getException().toString());

                    Toast.makeText(NewNoteActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(NewNoteActivity.this, MainActivity.class);
                    startActivity(mainIntent);

                }

            }
        });


    }



}
