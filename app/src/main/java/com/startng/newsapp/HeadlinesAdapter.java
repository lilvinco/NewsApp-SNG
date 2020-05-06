package com.startng.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HeadlinesAdapter extends ListAdapter<Note, HeadlinesAdapter.MyViewHolder> {
    //private String[] mDataset;
    //private Context mContext;
    int position;
    private OnItemClickListener listener;

    public HeadlinesAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getNote().equals(newItem.getNote());
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView noteItemView;


        public MyViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.Textview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    //private final LayoutInflater mInflater;
    //private List<Note> mNotes = new ArrayList<>();
    //private List<Note> mWords;

    // Provide a suitable constructor (depends on the kind of dataset)
   /* public HeadlinesAdapter(Context mContext) {
        mInflater = LayoutInflater.from(mContext);
    }*/

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Note mDataset = getItem(position);
        holder.noteItemView.setText(mDataset.getNote());

    }


    public Note getNoteAt(int position) {
        return getItem(position);
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}