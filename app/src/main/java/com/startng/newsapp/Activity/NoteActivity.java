package com.startng.newsapp.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.startng.newsapp.Database.Model.Note;
import com.startng.newsapp.R;
import com.startng.newsapp.ViewModel.NoteViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class NoteActivity extends AppCompatActivity {

    // public static final String NOTES_ID  = NoteListActivity.NOTES_ID;


    public static final String NOTES_ID = "com.example.android.notekeeper.NOTES_ID";
    public static final String ORIGINAL_NOTE_TEXT = "com.example.android.notekeeper.ORIGINAL_NOTE_TEXT";
    public static final String ORIGINAL_NOTE_ID = "com.example.android.notekeeper.ORIGINAL_NOTE_ID";
    public static final String ORIGINAL_NOTE_TITLE = "com.example.android.notekeeper.ORIGINAL_NOTE_TITLE";
    public static final String ORIGINAL_NOTE_TIME = "com.example.android.notekeeper.ORIGINAL_NOTE_TIME";
    public static final int ID_NOT_SET = -1;
    private static final String TAG = "SaveNote";
    private boolean clicked = false;

    private List<Note> notesList = new ArrayList<>();
    private EditText textNoteTitle;
    private EditText textNoteText;
    private TextView textNoteDateTime;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabShare;
    private FloatingActionButton fabWhatsapp;
    private NoteViewModel noteViewModel;

    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        //Toolbar toolbar = findViewById(R.id.toolbar_note);
        //setSupportActionBar(toolbar);

        textNoteTitle = findViewById(R.id.text_note_title);
        textNoteText = findViewById(R.id.text_note_text);
        textNoteDateTime = findViewById(R.id.text_note_date_and_time);
        fabEdit = findViewById(R.id.editFab);
        fabShare = findViewById(R.id.shareFabButton);
        fabWhatsapp = findViewById(R.id.whatsappFabButton);

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNoteText.setEnabled(true);
                textNoteTitle.setEnabled(true);
            }
        });
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
        fabWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWhatsapp();
            }
        });

        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra(ORIGINAL_NOTE_ID)) {
            setTitle(R.string.edit_activity_note);
            textNoteTitle.setText(intent.getStringExtra(ORIGINAL_NOTE_TITLE));
            textNoteText.setText(intent.getStringExtra(ORIGINAL_NOTE_TEXT));
            textNoteText.setEnabled(false);
            textNoteTitle.setEnabled(false);
            fabEdit.show();
            fabShare.show();
            fabWhatsapp.show();
        } else {
            setTitle(R.string.add_activity_note);
            fabEdit.hide();
            fabShare.hide();
            fabWhatsapp.hide();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int myId = item.getItemId();
        if (myId == R.id.action_save) {
            saveNote();
            clicked = true;
            Log.i(TAG, "it didn't work");
            return true;
        } else if (myId == R.id.action_send_mail) {
            sendEmail();
            return true;
        } else if (myId == R.id.action_cancel) {
            saveNote();
            clicked = true;
            return true;
        } /*else if (myId == R.id.action_next) {
            moveNext();
            return true;
        } */ else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = textNoteTitle.getText().toString();
        String text = textNoteText.getText().toString();
        String date;
        if (textNoteDateTime.getText().toString().isEmpty()) {
            //date = newDate();
            textNoteDateTime.setText(formatDate(newDate()));
        }
        //date = textNoteDateTime.getText().toString();
        date = newDate();

        if (title.trim().isEmpty() || text.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a non-empty title and note", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(ORIGINAL_NOTE_TITLE, title);
        data.putExtra(ORIGINAL_NOTE_TEXT, text);
        data.putExtra(ORIGINAL_NOTE_TIME, date);

        int id = getIntent().getIntExtra(ORIGINAL_NOTE_ID, -1);
        if (id != -1) {
            data.putExtra(ORIGINAL_NOTE_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();

    }

    private String newDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (clicked) {
            finish();
            return;
        } else {
            String title = textNoteTitle.getText().toString();
            String text = textNoteText.getText().toString();
            String date;
            if (textNoteDateTime.getText().toString().isEmpty()) {
                //date = newDate();
                textNoteDateTime.setText(formatDate(newDate()));
            }
            //date = textNoteDateTime.getText().toString();
            date = newDate();

            if (title.trim().isEmpty() || text.trim().isEmpty()) {
                Toast.makeText(this, "Please insert a non-empty title and note", Toast.LENGTH_SHORT).show();
                return;
            } else {
                int id = getIntent().getIntExtra(ORIGINAL_NOTE_ID, -1);
                if (id == -1) {
                    Note note = new Note(title, text, date);
                    noteViewModel.insert(note);
                } else {
                    Note note = new Note(title, text, date);
                    note.setNid(id);
                    noteViewModel.update(note);
                }
            }
            finish();
        }

    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 218
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("yy/MM/dd");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_next);
        int mNotePostion = getIntent().getIntExtra(ORIGINAL_NOTE_ID, -1);
        int lastNoteIndex = notesList.size();
        if (mNotePostion >= lastNoteIndex) {
            item.setEnabled(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void sendEmail() {
        String subject = textNoteTitle.getText().toString();
        String text = "\"" + textNoteText.getText().toString() + "\"\n";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void shareWhatsApp() {
        String contact = "+234 8184079896"; // use country code with your phone number
        String url = "https://api.whatsapp.com/";
        try {
            PackageManager pm = getApplicationContext().getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void sendWhatsapp() {
        String message = "\"" + textNoteText.getText().toString() + "\"\n";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }

    public void setEnabler(View view) {
        textNoteTitle.setEnabled(true);
        textNoteText.setEnabled(true);
    }

}
