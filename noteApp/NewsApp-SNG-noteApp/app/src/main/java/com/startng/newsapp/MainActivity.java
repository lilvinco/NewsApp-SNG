package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.startng.newsapp.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.startng.newsapp.EXTRA_DESCRIPTION";
    private EditText mtitle;
    private EditText mdescription;
    public static final String EXTRA_ID =  "com.startng.newsapp.EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtitle = findViewById(R.id.edit_text_title);
        mdescription = findViewById(R.id.edit_text_description);

        mtitle.setCursorVisible(false);
        mdescription.setCursorVisible(false);

        mtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtitle.setCursorVisible(true);
            }
        });

        mdescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdescription.setCursorVisible(true);
            }
        });



        mdescription = findViewById(R.id.edit_text_description);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            mtitle.setText(intent.getStringExtra(EXTRA_TITLE));
            mdescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        }else{
            setTitle("Add Note");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                saveNote();
                return true;
            case R.id.action_send:
                String message = mtitle.getText().toString() + "\n" + mdescription.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,message);
                startActivity(Intent.createChooser(intent,getResources().getString(R.string.share_using)));
                return true;// Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String title = mtitle.getText().toString();
        String description = mdescription.getText().toString();
        if (title.trim().isEmpty() && description.trim().isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Can't save empty note", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_DESCRIPTION, description);

            int id = getIntent().getIntExtra(EXTRA_ID,-1);
            if(id != -1){
                data.putExtra(EXTRA_ID,id);
            }
            setResult(RESULT_OK, data);
            finish();


    }
}
