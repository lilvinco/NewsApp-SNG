package com.startng.newsapp;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private ArrayList<Notes> notes;
    private LayoutInflater inflater;

    public NotesAdapter(Context context, ArrayList<Notes> notes){
        this.notes = notes;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        final Notes currentNote = notes.get(position);
        holder.noteContentTextView.setText(currentNote.getNoteContent());
        holder.noteTitleTextView.setText(currentNote.getNoteTitle());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitleTextView;
        TextView noteContentTextView;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.textViewTitle);
            noteContentTextView = itemView.findViewById(R.id.textViewBody);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                noteContentTextView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }
        }
    }
}
