package com.startng.newsapp.nav_drawer;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.startng.newsapp.R;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NavActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.action_save) {
                    Toast.makeText(NavActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId()== R.id.action_delete) {
                    Toast.makeText(NavActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_notes, R.id.nav_favourites, R.id.nav_trash, R.id.nav_settings, R.id.nav_invite, R.id.nav_log_out)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.nav_home:
                        Toast.makeText(NavActivity.this, "Local",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_notes:
                        Toast.makeText(NavActivity.this, "Notes",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_favourites:
                        Toast.makeText(NavActivity.this, "Favourites",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_trash:
                        Toast.makeText(NavActivity.this, "Trash",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_settings:
                        Toast.makeText(NavActivity.this, "Settings",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_invite:
                        Toast.makeText(NavActivity.this, "Share",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_log_out:
                        Toast.makeText(NavActivity.this, "Log Out",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
