package com.startng.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class WelcomeActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private NotesAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerLayout drawerLayout;
    private NoteDBHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Setup Toolbar and DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setNavigationDrawer();

        setUpRecyclerView();
        //button to create new Note
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, DetailActivity.class));
            }
        });
    }

    private void setUpRecyclerView() {
//        ArrayList<Notes> notes = new ArrayList<>();
//        notes.add(new Notes("Tales", "Boys in the street"));
//        notes.add(new Notes("Tales ver2", "In part 3 of the FirebaseUI Firestore tutorial, we will finally create our FirestoreRecyclerAdapter and display the documents from the Firestore database in our app.\n" +
//                "The FirestoreRecyclerAdapter is a subclass of the normal RecyclerView.Adapter and takes care of"));


        databaseHelper = new NoteDBHelper(this);
        mAdapter = new NotesAdapter(this);
        mAdapter.setNotes(DataManager.fetchAllNotes(databaseHelper));
        //setup recyclerview
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        //Use StaggeredGridLayoutManager
        layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setNavigationDrawer() {
        drawerLayout.closeDrawers();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        break;

                    case R.id.createCategory:
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
//
        //MenuItem menuItem = findViewById(R.id.app_bar_search);
        SearchView searchView =(SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void changeLayoutManager(MenuItem item) {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            layoutManager = new LinearLayoutManager(WelcomeActivity.this);
            item.setIcon(R.drawable.ic_view_gird);
        } else {
            layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
            item.setIcon(R.drawable.ic_view_linear);
        }
        mAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_change_view:
                changeLayoutManager(item);
                break;
            case R.id.app_bar_sort:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

