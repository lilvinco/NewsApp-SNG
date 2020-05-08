package com.startng.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.startng.newsapp.callbacks.NoteEventListener;

import java.util.ArrayList;

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.MyViewHolder> {
    private ArrayList<Note> mNote;
    private Context mContext;
    NoteEventListener listener;


    // Provide a suitable constructor (depends on the kind of dataset)
    public HeadlinesAdapter(Context context, ArrayList<Note> myDataset) {
        mNote = myDataset;
        mContext = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        TextView note_title, note_text, note_date;
        ImageView iv_more;

        public MyViewHolder(View itemView) {
            super(itemView);
            note_text = (TextView) itemView.findViewById(R.id.note_text);
            note_title = (TextView) itemView.findViewById(R.id.note_title);
            note_date = (TextView) itemView.findViewById(R.id.note_date);
            iv_more = (ImageView) itemView.findViewById(R.id.iv_more);
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public HeadlinesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_item, parent, false);
        return new MyViewHolder(v);
    }

    public Note getNote(int position) {
        return mNote.get(position);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataSet at this position
        // - replace the contents of the view with that element
        final Note note = getNote(position);

        if (note != null){
            holder.note_title.setText(note.getNoteTitle());
            holder.note_text.setText(note.getNoteText());
            holder.note_date.setText(NoteUtils.dateFromLong(note.getNoteDate()));

            //initializing the click events
            holder.iv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNoteClick(note);
                }
            });

            holder.iv_more.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onNoteLongClick(note);
                    return false;
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onNoteLongClick(note);
                    return false;
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra("title", note.getNoteTitle());
                    intent.putExtra("headline", note.getNoteText());
                    mContext.startActivity(intent);
                }
            });
//


        }

    }

    // Return the size of your dataSet (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mNote.size();
    }

    public void setListener(NoteEventListener listener) {
        this.listener = listener;
    }
}