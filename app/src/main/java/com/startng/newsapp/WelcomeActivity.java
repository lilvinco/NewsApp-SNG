package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DrawerLayout drawerLayout;
    int number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setNavigationDrawer();

        ArrayList<Notes> notesArrayList = new ArrayList<>();
        notesArrayList.add(new Notes("Millionaire at 21","The above quote is from “Delivering a Lecture,” a chapter in Barbara Gross Davis’ classic text Tools for Teaching. That chapter is an excellent resource for learning how to lecture well. See also Davis’ chapter, “Preparing to Teach the Large Lecture Course.”" ));

        notesArrayList.add(new Notes("Mark Zukerberg", "“Lecturing is not simply a matter of standing in front of a class and reciting what you know. The classroom\n" +
                " lecture is a special form of communication in which voice, gesture, movement, facial expression, and eye\n" +
                "contact can either complement or detract from the content. No matter what your topic, your delivery and manner of speaking immeasurably influence your students’ attentiveness and learning.”\n" +
                "The above quote is from “Delivering a Lecture,” a chapter in Barbara Gross Davis’ classic text Tools for Teaching. That chapter is an excellent resource for learning how to lecture well. See also Davis’ chapter, “Preparing to Teach the Large Lecture Course.”\n"));

        notesArrayList.add(new Notes("COVID 19 - Pandemic", "When planning a lecture, keep in mind that you have control or influence over several elements of your classroom:"));

        notesArrayList.add(new Notes("How to make money in 90 days", "– The slides and other visual aids you use can either complement or confuse your verbal message, depending on how you design them. Consider how photos and other images might function as metaphors that make your points more memorable."));

        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        //Used in StaggeredGridLayoutManager
        layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new NotesAdapter(this, notesArrayList);
        recyclerView.setAdapter(mAdapter);


        //button to create new Note
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setNavigationDrawer(){
        drawerLayout = findViewById(R.id.drawer_layout);

        drawerLayout.closeDrawers();

        NavigationView navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Toast.makeText(WelcomeActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void changeLayoutManager(MenuItem item){
        if (layoutManager instanceof StaggeredGridLayoutManager){
            layoutManager = new LinearLayoutManager(WelcomeActivity.this);
            item.setIcon(R.drawable.ic_view_gird);
        } else {
            layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
            item.setIcon(R.drawable.ic_view_linear);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.app_bar_change_view:
                changeLayoutManager(item);
                break;
            case R.id.app_bar_sort:
                break;
            case R.id.app_bar_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

