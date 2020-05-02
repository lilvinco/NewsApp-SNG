package com.startng.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //setup back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            //respond to Save click
            case R.id.save:
                //TODO: Work on Save Click
                break;
            //respond to Category click
            case R.id.category:
                //TODO: Work on Category Click
                break;
            //respond to Reminder click
            case R.id.reminder:
                //TODO: Work on Reminder Click
                break;
            //respond to Delete click
            case R.id.delete:
                //TODO: Work on Delete Click
                break;
            //respond to Share click
            case R.id.share:
                //TODO: Work on Share Click
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
