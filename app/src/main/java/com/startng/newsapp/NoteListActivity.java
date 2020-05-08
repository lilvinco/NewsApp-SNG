package com.startng.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteListActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mRecyclerLayout;
    private ArrayList<MNote> noteList;
    private static final int CREATE_NEW_NOTE_CODE = 0x1000;
//    private static final int EDIT_EXISTING_NOTE_CODE = 0x2000;
    private static final int DELETE_NOTE_CODE = 0x3000;
    private static final int TO_EDIT_NOTE = 0x5000;
//    public final String TAG = "NoteListActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list);

        mRecyclerView = findViewById(R.id.recycler_view);
        //setting layout manager
        mRecyclerLayout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRecyclerLayout);
        //setting recycler adapter
        noteList = MNote.getNotes();
        mRecyclerAdapter = new MNoteAdapter(noteList);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    public void listToCreateNew(View view) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        startActivityForResult(intent, CREATE_NEW_NOTE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CREATE_NEW_NOTE_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String noteTitle = data.getStringExtra("NEW_NOTE_TITLE");
                String noteText = data.getStringExtra("NEW_NOTE_TEXT");
                MNote newNote = new MNote(noteTitle, noteText);
                noteList.add(newNote);
                Log.e("add_this_note",newNote.toString());
                mRecyclerAdapter.notifyDataSetChanged();
            }
        }
        if(requestCode == DELETE_NOTE_CODE) {
            if(resultCode == Activity.RESULT_OK){
                MNote noteToDelete = data.getParcelableExtra("NOTE_TO_DELETE");
                int noteToDeletePosition = data.getIntExtra("NOTE_TO_DELETE_POSITION",0);
                Log.e("delete_this_note",noteToDelete.toString());
                Log.e("delete_this_note", String.valueOf(noteToDeletePosition));
                noteList.remove(noteToDelete);
                mRecyclerAdapter.notifyItemRemoved(noteToDeletePosition);
            }
            else if(resultCode == Activity.RESULT_FIRST_USER){

            }
        }
//        if(requestCode == TO_EDIT_NOTE) {
//            if(resultCode == Activity.RESULT_FIRST_USER) {
//                MNote noteToPut = data.getParcelableExtra("UPDATED_NOTE");
//                int position = data.getIntExtra("UPDATED_NOTE_POSITION", 0);
//                noteList.add(position, noteToPut);
//                mRecyclerAdapter.notifyItemChanged(position);
//            }
//        }

    }



}
