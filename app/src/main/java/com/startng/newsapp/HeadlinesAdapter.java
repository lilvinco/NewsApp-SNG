
package com.startng.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.MyViewHolder>  {
    private Context context;
    private ArrayList<HeadlinesActivity> notes;
    private NoteEventListener listener;
    private boolean multiCheckMode = false;

    public HeadlinesAdapter(Context context, ArrayList<HeadlinesActivity> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.headline_item, parent, false);

        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final HeadlinesActivity note = getNote(position);
        if (note != null) {
            holder.noteText.setText(note.getNoteText());
            holder.noteDate.setText(NoteUtils.dateFromLong(note.getNoteDate()));
        }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onNoteClick(note);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onNoteLongClick(note);
                    return false;
                }
            });

            if (multiCheckMode) {
               holder.checkBox.setVisibility(View. VISIBLE);
               holder.checkBox.setChecked(note.isChecked());
            } else holder.checkBox.setVisibility(View.GONE);

        }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private  HeadlinesActivity getNote(int position) {
        return notes.get(position);
    }
    public List<HeadlinesActivity> getCheckedNotes() {
        List<HeadlinesActivity> checkedNotes = new ArrayList<>();
        for (HeadlinesActivity n : this.notes) {
            if (n.isChecked())
                checkedNotes.add(n);
        }
        return checkedNotes;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView noteText, noteDate;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            noteDate = itemView.findViewById(R.id.note_date);
            noteText = itemView.findViewById(R.id.note_text);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public void setListener(NoteEventListener listener) {
        this.listener = listener;
    }

    public void setMultiCheckMode(boolean multiCheckMode) {
        this.multiCheckMode = multiCheckMode;
        if (!multiCheckMode)
            for (HeadlinesActivity note : this.notes) {
                note.setChecked(false);
            }
        notifyDataSetChanged();
    }
}

