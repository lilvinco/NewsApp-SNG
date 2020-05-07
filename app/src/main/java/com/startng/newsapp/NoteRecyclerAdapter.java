package com.startng.newsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder> {
    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Note> mNotes;
    private OnDeleteClickListener onDeleteClickListener;

    public NoteRecyclerAdapter(Context context, OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(layoutInflater.inflate(R.layout.note_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, final int position) {
        if (mNotes != null) {
            Note note = mNotes.get(position);
            holder.setData(note.getNoteTitle(), note.getNoteContent(), position);
            holder.setListeners();
        }
        else {
            holder.textNoteTitle.setText("No Title");
            holder.textNoteContent.setText("No Content");
        }
    }

    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }

    public void setNotes(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView textNoteTitle;
        private TextView textNoteContent;
        private ImageView imageDelete;
        private int mPosition;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textNoteTitle = itemView.findViewById(R.id.text_note_title);
            textNoteContent = itemView.findViewById(R.id.text_note_content);
            imageDelete = itemView.findViewById(R.id.image_delete);
        }

        public void setData(String noteTitle, String noteContent, int position) {
            if (noteTitle.isEmpty()) {
                textNoteTitle.setVisibility(View.GONE);
            }
            else {
                textNoteTitle.setVisibility(View.VISIBLE);
            }
            textNoteTitle.setText(noteTitle);

            if (noteContent.isEmpty()) {
                textNoteContent.setVisibility(View.GONE);
            }
            else {
                textNoteContent.setVisibility(View.VISIBLE);
            }
            textNoteContent.setText(noteContent);
            mPosition = position;
        }

        public void setListeners() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditActivity.class);
                    intent.putExtra(EditActivity.NOTE_ID, mNotes.get(mPosition).getNoteId());
                    ((Activity)mContext).startActivityForResult(intent, NoteListActivity.UPDATE_REQUEST_CODE);
                }
            });

            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDeleteClickListener != null) {
                        onDeleteClickListener.OnDeleteClickListener(mNotes.get(mPosition));
                    }
                }
            });
        }
    }

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Note myNote);
    }
}