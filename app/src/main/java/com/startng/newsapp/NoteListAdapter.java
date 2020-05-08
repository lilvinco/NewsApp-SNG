package com.startng.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.MyViewHolder> {
    private ArrayList<Note> notes;
    private android.content.Context context;
    private NoteEventListener listener;

    public NoteListAdapter(ArrayList<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }



    // Provide a suitable constructor (depends on the kind of dataset)

    // Create new views (invoked by the layout manager)
    @Override
    public NoteListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.note_item,parent,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Note note = getNote(position);
        if (note!=null) {
            holder.noteText.setText(note.getNoteText());
            holder.noteDate.setText(NoteUtils.dateFromLong(note.getNoteDate()));
            //click event
             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     listener.onNoteClick(note);
                 }
             });

             // Long click event
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onNoteLongClick(note);
                    return false;
                }
            });
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView noteText;
        public TextView noteDate;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.text_note);
            noteDate = itemView.findViewById(R.id.note_date);
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return notes.size();
    }

    public Note getNote(int position){
        return notes.get(position);
    }

    public void setListener(NoteEventListener listener) {
        this.listener = listener;
    }
}