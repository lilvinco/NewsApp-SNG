package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find textviews, get the extras and assign both to each other
//        TextView textView = findViewById(R.id.textView3);
//        String headline = getIntent().getStringExtra("headline");
//        textView.setText(headline);
        gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
//
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);


        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Notes")
                    .child(firebaseAuth.getCurrentUser().getUid());
        }


        updateUI();


    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();


    }

    private void loadData() {
//        super.onResume();
        Query query = databaseReference.orderByChild("timestamp");

        FirebaseRecyclerAdapter<NoteModel, HeadlinesAdapter> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NoteModel,
                HeadlinesAdapter>(NoteModel.class, R.layout.single_note_layout, HeadlinesAdapter.class, query) {
            @Override
            protected void populateViewHolder(final HeadlinesAdapter headlinesAdapter, NoteModel noteModel, int i) {
                final String noteId = getRef(i).getKey();

                databaseReference.child(noteId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("title") && dataSnapshot.hasChild("timestamp")) {
                            String title = dataSnapshot.child("title").getValue().toString();
                            String timestamp = dataSnapshot.child("timestamp").getValue().toString();

                            headlinesAdapter.setNoteTitle(title);
//                            headlinesAdapter.setNoteTime(timestamp);
                            GetTime getTime = new GetTime();
                            headlinesAdapter.setNoteTime(getTime.getTime(Long.parseLong(timestamp), getApplicationContext()));

                            headlinesAdapter.cardView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                                    intent.putExtra("noteId", noteId);
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    private void updateUI() {

        if (firebaseAuth.getCurrentUser() != null) {
            Log.i("MainActivity", "firebaseAuth != null");






        }
        else {

            Log.i("MainActivity", "firebaseAuth == null");
//            Intent intent = new Intent(MainActivity.this, Start_Activity.class);
//            startActivity(intent);
//            finish();



        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.main_new_note_btn:
                Intent menuIntent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivity(menuIntent);
                break;
        }
        return true;
    }
}


