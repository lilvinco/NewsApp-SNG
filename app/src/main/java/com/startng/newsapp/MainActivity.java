package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
=======
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText mNote;
    EditText eNote;
    Button savedButton;
>>>>>>> app commit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        //Find textviews, get the extras and assign both to each other
        TextView textView = findViewById(R.id.textView3);
        String headline = getIntent().getStringExtra("headline");
        textView.setText(headline);
=======
        name = findViewById(R.id.note);
        mNote = findViewById(R.id.note);
        eNote = findViewById(R.id.note);
        savedButton = findViewById(R.id.savedBtn);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();

        savedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Saved to database


                for (int i = 0; i < 1; i++) {

                    User user = new User(name.getText().toString(), mNote.getText().toString(), eNote.getText().toString());
                    db.userDao().insertAll(user);

                }
                startActivity(new Intent(MainActivity.this, HeadlinesActivity.class));
                finish();


            }
        });











//        Find textviews, get the extras and assign both to each other
//        TextView textView = findViewById(R.id.textView3);
//        String headline = getIntent().getStringExtra("headline");
//        textView.setText(headline);
>>>>>>> app commit
    }
}
