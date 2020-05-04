package com.startng.newsapp;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataManager {
    private final static String NOTE_COLLECTIONS = "Notes";

    private final static String TITLE_FIELD = "Title";
    private final static String CONTENT_FIELD = "Content";


    //TODO: implement writing to DB
    static void addToDB(Notes note) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> noteMap = new HashMap<>();
        noteMap.put(TITLE_FIELD, note.getNoteTitle());
        noteMap.put(CONTENT_FIELD, note.getNoteContent());

        db.collection(NOTE_COLLECTIONS).add(noteMap);
    }

    //TODO: implement reading from DB
    static ArrayList<Notes> readFromDB() {
        final ArrayList<Notes> myNotes = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(NOTE_COLLECTIONS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        String title = Objects.requireNonNull(documentSnapshot.get(TITLE_FIELD)).toString();
                        String content = Objects.requireNonNull(documentSnapshot.get(CONTENT_FIELD)).toString();

                        myNotes.add(new Notes(title, content));
                    }
                }
            }
        });

        return myNotes;
    }
}
