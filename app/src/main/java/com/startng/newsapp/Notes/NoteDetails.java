package com.startng.newsapp.Notes;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.startng.newsapp.R;

public class NoteDetails extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    DocumentReference documentReference;

    private String userID = firebaseUser.getUid();

    private TextView mContent;

    String mTitle, mmContent, mNoteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        mContent = findViewById(R.id.note_details_content);
        mContent.setMovementMethod(new ScrollingMovementMethod()); // Make the textView scrollable

        initializeData();

    }

    private void getContents(String title, String content, String noteID) {
        this.mmContent = content;
        this.mTitle = title;
        this.mNoteID = noteID;
    }

    /**
     * @param item will be any menu item in the toolbar
     * Pressing the back button on the toolbar returns to the previous activity
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {  // The up button by default has home as ID
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void editNotes(View view) {
        view.setEnabled(false);

        Intent intent = new Intent(this, AddNotes.class);
        intent.putExtra("title", mTitle);
        intent.putExtra("content", mmContent);
        intent.putExtra("noteID", mNoteID);
        startActivity(intent);


        view.setEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh data here
        initializeData();

    }

    private void initializeData() {

        Intent data = getIntent();
        final String NoteID = data.getStringExtra("note_ID");


        documentReference = firebaseFirestore.collection("notes")
                .document(userID)
                .collection("myNote")
                .document(NoteID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

                String title  = documentSnapshot.getString("title");
                String content = documentSnapshot.getString("content");

                getSupportActionBar().setTitle(title);
                mContent.setText(content);

                getContents(title, content, NoteID);

            }
        });

    }
}
