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

    private ArrayList<Notes> notes, notesArrayList;
    private LayoutInflater inflater;
    private Context context;

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitleTextView;
        TextView noteContentTextView;
        int currentPosition;

        NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.textViewTitle);
            noteContentTextView = itemView.findViewById(R.id.textViewBody);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                noteContentTextView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Intent intent = new Intent(context.getApplicationContext(),DetailActivity.class);
//                intent.putExtra(DetailActivity.NOTE_TITLE_TAG, notes.get(currentPosition).getNoteTitle());
//                intent.putExtra(DetailActivity.NOTE_CONTENT_TAG, notes.get(currentPosition).getNoteContent());
//                Log.v("MApp", "Position for Detail Activity " + currentPosition);
//                context.startActivity(intent);
                }
            });
        }

        void setData(String title, String content, int position) {
            noteContentTextView.setText(content);
            noteTitleTextView.setText(title);
            currentPosition = position;
        }
    }
}