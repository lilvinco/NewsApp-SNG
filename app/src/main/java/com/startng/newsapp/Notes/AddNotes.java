package com.startng.newsapp.Notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.startng.newsapp.Helper.ToastAndSnacksbar;
import com.startng.newsapp.R;

import java.util.HashMap;
import java.util.Map;

public class AddNotes extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    private String userID = firebaseUser.getUid();

    private EditText noteContent, noteTitle;
    private Button mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        final Intent data = getIntent();

        mSave = findViewById(R.id.save_button);

        noteContent = findViewById(R.id.note_details_content);
        noteTitle = findViewById(R.id.add_title);

        final String intentTitle = data.getStringExtra("title");
        final String intentContent = data.getStringExtra("content");
        final String intentNoteID = data.getStringExtra("noteID");

        if (!(intentTitle == null)) {
            setVariables(intentTitle, intentContent, intentNoteID);
        }

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteContent.setError(null);

                String mTitle = noteTitle.getText().toString().trim();
                String mContent = noteContent.getText().toString().trim();

                if (mContent.isEmpty() || mTitle.isEmpty()) {
                    ToastAndSnacksbar.SnackMessage(getBaseContext(), view, "Field(s) cannot be empty");
                    return;
                }

                if (!(intentTitle == null)) {
                    updateNotes(mTitle, mContent, view, intentNoteID);
                } else {
                    addNote(mTitle, mContent, view);
                }

            }
        });

    }


    private void setVariables(String title, String content, String intentNoteID) {
        noteTitle.setText(title);
        noteContent.setText(content);
        getSupportActionBar().setTitle("Edit note");

    }

    private void updateNotes(String mTitle, String mContent, final View view, String intentNoteID) {

        DocumentReference documentReference = firebaseFirestore.collection("notes")
                .document(userID)
                .collection("myNote")
                .document(intentNoteID);

        Map<String, Object> addMap = new HashMap<>();

        addMap.put("title", mTitle);
        addMap.put("content", mContent);
        addMap.put("time", FieldValue.serverTimestamp());

        documentReference.update(addMap);
        ToastAndSnacksbar.ToastMessage(getApplicationContext(), view, "Note updated");
    }

    private void addNote(String mTitle, String mContent, final View view) {

        DocumentReference documentReference = firebaseFirestore.collection("notes")
                .document(userID)
                .collection("myNote")
                .document();

        Map<String, Object> addMap = new HashMap<>();

        addMap.put("title", mTitle);
        addMap.put("content", mContent);
        addMap.put("time", FieldValue.serverTimestamp());

        view.setEnabled(false);

        documentReference.set(addMap);
        ToastAndSnacksbar.ToastMessage(getApplicationContext(), view, "Note saved");
    }

}
