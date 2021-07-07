package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.startng.newsapp.database.Notes;
import com.startng.newsapp.database.NotesViewModel;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditorActivity extends AppCompatActivity {
    Date date;
    NotesViewModel notesViewModel;
    EditText editText;
    LinearLayout view;
    TextView currentNoteTime;
    Toolbar toolbar;
    int selectedColor;
    SpectrumPalette palette;
    int itemId;
    private boolean mNoteHasChanged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        view = findViewById(R.id.rootView);
        editText = findViewById(R.id.edit);
        currentNoteTime = findViewById(R.id.note_current_time);
        toolbar = findViewById(R.id.toolbar);
        palette = findViewById(R.id.colorSelector);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        //Getting itemId from main activity via intent
        Intent intent = getIntent();
        itemId = intent.getIntExtra("item_id",0);

        //Logging the id of the response from roomDb
        Log.e("print item id", String.valueOf(itemId));

        toolbar.inflateMenu(R.menu.editor_menu);

        mNoteHasChanged = false;

        if(itemId == 0){
            toolbar.setTitle(getString(R.string.add_a_note));
            editText.requestFocus();
            mNoteHasChanged = true;
            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a note that hasn't been created yet.)
            MenuItem menuItem = toolbar.getMenu().findItem(R.id.action_delete);
            menuItem.setVisible(false);
            //color picker preference
            palette.setSelectedColor(getResources().getIntArray(R.array.slider_colors)[0]);
            selectedColor = getResources().getIntArray(R.array.slider_colors)[0];
        }else{
            toolbar.setTitle(getString(R.string.edit_a_note));
            //load existing info here
            getSingleNote();
        }

        //set up listener to check if user is currently changing any field on the form
        View.OnTouchListener mTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mNoteHasChanged = true;
                return false;
            }
        };

        editText.setOnTouchListener(mTouchListener);

        palette.setOnColorSelectedListener(new SpectrumPalette.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                selectedColor = color;
                getWindow().setStatusBarColor(color);
                view.setBackgroundColor(color);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        // Respond to a click on the "Save" menu option
                        saveNote();
                        finish();
                        return true;
                    case R.id.action_delete:
                        // Respond to a click on the "Delete" menu option
                        showDeleteConfirmationDialog();
                        return true;
                }
                return false;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the note hasn't changed, continue with navigating up to parent activity
                // which is the {@link MainActivity}.
                if (!mNoteHasChanged) {
                    finish();
                    return;
                }
                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                finish();
                            }
                        };
                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
            }
        });

        //Get current time from android device as a Date object to be stored in room
        DateFormat df = new SimpleDateFormat("EEEE, dd MMM yyyy hh:mm a", Locale.ENGLISH);
        date = Calendar.getInstance().getTime();
        currentNoteTime.setText(df.format(date));

    }

    private void saveNote(){
        String noteDesc = editText.getText().toString().trim();
        if(TextUtils.isEmpty(noteDesc)){
            Toast.makeText(this,"Cannot save empty note",Toast.LENGTH_SHORT).show();
            return;
        }

        if(itemId == 0) {
            //Saving a new note
            notesViewModel.insertNote(new Notes(date,noteDesc,selectedColor));
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }else{
            //Updating an existing note
            notesViewModel.updateNote(new Notes(itemId,Calendar.getInstance().getTime(),noteDesc,selectedColor));
            Toast.makeText(this,"Note updated",Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteNote(){
        if (itemId != 0) {
            //Deleting an existing note
            notesViewModel.deleteNote(new Notes(itemId,getSingleNote().getNoteTime(),getSingleNote().getNoteDesc(),getSingleNote().getNoteColor()));
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        // Create a MaterialAlertDialog and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the note.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        // Create and show the AlertDialog
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        // Create a MaterialAlertDialog and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the note.
                deleteNote();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the note.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        // Create and show the AlertDialog
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling back button press
        if (!mNoteHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private Notes getSingleNote(){
        final Notes[] instance = {new Notes()};
        notesViewModel.getSingleNote(itemId).observe(this, new Observer<Notes>() {
            @Override
            public void onChanged(Notes notes) {
                if(notes != null){
                    instance[0] = notes;
                    DateFormat df = new SimpleDateFormat("EEEE, dd MMM yyyy hh:mm a", Locale.ENGLISH);
                    currentNoteTime.setText(df.format(notes.getNoteTime()));
                    editText.setText(notes.getNoteDesc());
                    palette.setSelectedColor(notes.getNoteColor());
                    selectedColor = notes.getNoteColor();
                }
            }
        });
        return instance[0];
    }

}
