package com.startng.newsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HeadlinesAdapter extends ListAdapter<Notes, HeadlinesAdapter.MyViewHolder> {

    private static OnItemClickListener listener;

    public HeadlinesAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Notes> DIFF_CALLBACK =new DiffUtil.ItemCallback<Notes>() {
        @Override
        public boolean areItemsTheSame( Notes oldItem,  Notes newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription());
        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;

        public MyViewHolder(View ItemView) {
            super(ItemView);
            title = ItemView.findViewById(R.id.title);
            description = ItemView.findViewById(R.id.edit_text_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positon = getAdapterPosition();
                    if (listener != null && positon != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(getItem(positon));
                    }
                }
            });

        }

    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.headline_item, parent, false);
        return new MyViewHolder(view);

    }

    public Notes getNoteAtPosition(int position) {
        return getItem(position);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notes currentNote = getItem(position);
        holder.title.setText(currentNote.getTitle());
        holder.description.setText(currentNote.getDescription());
        if(currentNote.getTitle().isEmpty()){
            holder.title.setVisibility(View.GONE);
        } else if(currentNote.getDescription().isEmpty()){
            holder.description.setVisibility(View.GONE);
        }

    }



    public interface OnItemClickListener {
        void OnItemClick(Notes notes);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

