package com.startng.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.MyViewHolder> {
    private String[] mDataset;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public EditText noteItemView;


        public MyViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.EdAdd);
        }
    }
    private final LayoutInflater mInflater;
    private List<Note> mWords = new ArrayList<>();
    //private List<Note> mWords;

    // Provide a suitable constructor (depends on the kind of dataset)
    public HeadlinesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemView = mInflater.inflate(R.layout.activity_main, parent, false);
        return new MyViewHolder(itemView);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

            Note mDataset = mWords.get(position);
            holder.noteItemView.setText(mDataset.getNote());
        /*holder.noteItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("headline", mDataset[position]);
                mContext.startActivity(intent);
            }
        });*/
    }

    public void setNotes(List<Note> notes) {
        this.mWords = notes;
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
       /* if (mWords !=null)
            return mWords.size();
        else return 0;*/
        return mWords.size();
    }
}