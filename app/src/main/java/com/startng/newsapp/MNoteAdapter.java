package com.startng.newsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MNoteAdapter extends RecyclerView.Adapter<MNoteAdapter.ViewHolder> {
    private ArrayList<MNote> notesList;
    private Context context;
    private static final int DELETE_NOTE_CODE = 0x3000;


    public MNoteAdapter(ArrayList<MNote> nList){
        this.notesList = nList;
    }

//    public static ArrayList<MNote> getNotesList() {
//        return notesList;
//    }
//
//    public static void setNotesList(ArrayList<MNote> updatedNote) {
//        notesList = updatedNote;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTitle;
        public TextView noteText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            noteTitle = itemView.findViewById(R.id.note_title);
            noteText = itemView.findViewById(R.id.note_text);
        }
    }

    @NonNull
    @Override
    public MNoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context nContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(nContext);

        //Inflate the custom layout
        View noteItem = inflater.inflate(R.layout.note_list_item, parent, false);

        //Return a new viewholder instance
        ViewHolder viewHolder = new ViewHolder(noteItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MNoteAdapter.ViewHolder holder, final int position) {
        //Get the data model based on position
        final MNote note = notesList.get(position);
        TextView nTitle = holder.noteTitle;
        TextView nText = holder.noteText;
        nTitle.setText(note.getNoteTitle());
        nText.setText(note.getNoteText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewNoteActivity.class);
                intent.putExtra("EXISTING_NOTE", note);
                intent.putExtra("EXISTING_NOTE_POSITION", position);
                ((Activity)context).startActivityForResult(intent, DELETE_NOTE_CODE);
            }
        });
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void removeItemAtPosition(int position) {
        notesList.remove(position);
    }

}
