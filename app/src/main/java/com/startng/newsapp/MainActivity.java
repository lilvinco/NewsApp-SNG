package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView ;
    private MainAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    MaterialToolbar mToolbar;




    public static final   String Note_Id = "note_id";
    public static final  String Note_Title = "note_title";
    public static final   String Note_content = "content";


    ArrayList<RecyclerItems> listItems;

    DatabaseReference databaseNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseNotes = FirebaseDatabase.getInstance().getReference("Notes");


        mToolbar = (MaterialToolbar) findViewById(R.id.topAppBar);
        setSupportActionBar(mToolbar);

         listItems = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseNotes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listItems.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren())
                {
                    RecyclerItems items = snap.getValue(RecyclerItems.class);

                    listItems.add(items);
                }
                recyclerView = findViewById(R.id.my_recycler_view);
                recyclerView.setHasFixedSize(true);
                layoutManager= new LinearLayoutManager(getApplicationContext());
                mAdapter = new MainAdapter(listItems);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter);


                mAdapter.setOnItemClickListerner(new MainAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String id = listItems.get(position).getNoteId();
                        String title = listItems.get(position).getNoteTitle();
                        String content = listItems.get(position).getContent();


                        Toast.makeText(MainActivity.this, "Long click to delete", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this , UpdateNote.class);
                        intent.putExtra(Note_Id, id);
                        intent.putExtra(Note_Title, title);
                        intent.putExtra(Note_content, content);


                        startActivity(intent);

                    }

                    @Override
                    public void onDeleteClick(int position) {
                        listItems.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                });

                mAdapter.setonLongItemClickListener(new MainAdapter.onLongItemClickListener() {
                    @Override
                    public boolean onLongClick(int position) {
                        // long click to delete

                        String id = listItems.get(position).getNoteId();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(id);
                        databaseReference.removeValue();
                        return true;
                    }
                });
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_app_bar,menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();


        if(itemId == R.id.create_new){

           Intent intent = new Intent(MainActivity.this,CreateNoteActivity.class);
           startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
