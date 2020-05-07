package com.startng.newsapp;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class NotesAdapter extends FirestoreRecyclerAdapter {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NotesAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull Object model) {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }
}
