package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
<<<<<<< HEAD
=======
import androidx.room.Room;
>>>>>>> app commit

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
import android.widget.TextView;

public class HeadlinesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    int number;

=======
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class HeadlinesActivity extends AppCompatActivity {
     Button button;
     RecyclerView recyclerView;
     RecyclerView.Adapter adapter;
 //    ArrayList<User>users;
//     RecyclerView.LayoutManager layoutManager;
    int number;


>>>>>>> app commit
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headlines);
<<<<<<< HEAD


        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        String[] myDataset = getResources().getStringArray(R.array.sports_info);
        mAdapter = new HeadlinesAdapter(this, myDataset);
        recyclerView.setAdapter(mAdapter);

    }
=======
        button = findViewById(R.id.button);

//

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();

        List<User> users = db.userDao().getAllUsers();


        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(users);
        recyclerView.setAdapter(adapter);
 // use this setting to improve performance if you know that changes
 // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

// use a linear layout manager

        // specify an adapter (see also next example)
//        String[] myDataset = getResources().getStringArray(R.array.sports_info);
//        mAdapter = new HeadlinesAdapter(this, myDataset);
//        recyclerView.setAdapter(mAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HeadlinesActivity.this, MainActivity.class));

            }
        });

    }
    //save button method
    }
>>>>>>> app commit

   /* public void addNumber(View view) {
        number++;
        TextView textView = findViewById(R.id.numbertextView);
        textView.setText(String.valueOf(number));
    }*/
<<<<<<< HEAD
}
=======

>>>>>>> app commit

