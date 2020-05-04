package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.startng.newsapp.Authentication.RegLog;
import com.startng.newsapp.Models.NotesModel;
import com.startng.newsapp.Notes.AddNotes;
import com.startng.newsapp.Notes.NoteDetails;
import com.startng.newsapp.Notes.NotesAdapter;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private NotesAdapter notesAdapter;
    private RecyclerView noteListRecyclerView;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference notesReference = firebaseFirestore.collection("notes");

    private String userID = firebaseUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNotes.class));
            }
        });

        noteListRecyclerView = findViewById(R.id.notes_recyclerview);
        setupRecyclerView();


    }

    private void setupRecyclerView() {

        Query query = notesReference.document(userID).collection("myNote")
                .orderBy("time", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<NotesModel> options = new FirestoreRecyclerOptions.Builder<NotesModel>()
                .setQuery(query, NotesModel.class)
                .build();

        notesAdapter = new NotesAdapter(options, this);
        noteListRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        noteListRecyclerView.setAdapter(notesAdapter);

        /**
         * Swipe to delete effect
         * SimpleCallback(0,0) has two parameters
         * First is for drag and drop
         * Second is for LEFT or RIGHT movement
         */

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                deleteNotes(viewHolder.getAdapterPosition());
            }

        }).attachToRecyclerView(noteListRecyclerView);

        /**
         * notesAdapter.setOnItemClickListener overides two methods
         * onItemClick --->  Clicks for the entire view
         * onOptionMenuClick  --->  Clicks for the popUpMenu
         */

        notesAdapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {

            // Clicks for the entire view
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                // Get NoteID from documentSnapshot
                String NoteID = documentSnapshot.getId();

                Intent intent = new Intent(MainActivity.this, NoteDetails.class);
                intent.putExtra("note_ID", NoteID);
                startActivity(intent);

            }

            // Clicks for the popUpMenu
            @Override
            public void onOptionMenuClick(DocumentSnapshot documentSnapshot, final int position, View view) {

                // Get NoteID from documentSnapshot
                final String NoteID = documentSnapshot.getId();

                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);  // Create a popUpMenu
                popupMenu.getMenu().add("Edit note").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // To edit notes
                        editNotes(NoteID);
                        return false;
                    }
                });

                popupMenu.getMenu().add("Delete note").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // To delete notes
                        deleteNotes(position);
                        return false;
                    }
                });

                popupMenu.show();

            }

        });

    }

    private void deleteNotes(int position) {
        notesAdapter.deleteNote(position);  // Delete a note
    }

    private void editNotes(final String NoteID) {

        DocumentReference documentReference
                = notesReference.document(NoteID); // Append the firestoreCollectionReference with DocumentID

        documentReference.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

                assert documentSnapshot != null;
                String title = documentSnapshot.getString("title");
                String content = documentSnapshot.getString("content");

                Intent intent = new Intent(MainActivity.this, AddNotes.class);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("noteID", NoteID);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        notesAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        notesAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_out_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            case R.id.log_out_btn:
                checkUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkUser() {
        if(firebaseUser.isAnonymous()){
            dialogWarningAnonymous();
        } else {
            dialogWarningAuthUser();

        }
    }

    private void dialogWarningAuthUser() {
        final AlertDialog.Builder alerDialog = new AlertDialog.Builder(this);
        alerDialog.setTitle("Do you want to leave?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        startActivity(new Intent(MainActivity.this, RegLog.class));
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alerDialog.show();
    }

    private void dialogWarningAnonymous() {
        AlertDialog.Builder warningDialog = new AlertDialog.Builder(this);

        warningDialog.setTitle("You are temporary logged in.")
                .setMessage("You are logged in with Temporary account. " +
                        "Logging out will delete all notes")
//                .setPositiveButton("Save notes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //ToDo Create RegisterActivity
//                        // startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
//                    }
//                })
                .setNegativeButton("log out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // ToDo Delete all notes by anonymous user

                // Delete anonymous user
                firebaseUser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(MainActivity.this, RegLog.class));

                        finish();

                    }
                });

//                startActivity(new Intent(MainActivity.this, RegLog.class));

                finish();


            }
        });

        warningDialog.show(); // Inflate the Dialog
    }
}
