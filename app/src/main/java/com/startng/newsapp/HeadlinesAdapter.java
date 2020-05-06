package com.startng.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.MyViewHolder> {
    private Context context;
    private  List<Note> list;
    private  OnNoteItemClick onNoteItemClick;
    private  LayoutInflater layoutInflater;

    public  HeadlinesAdapter(List<Note> list, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onNoteItemClick = (OnNoteItemClick) context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = layoutInflater.inflate(R.layout.all_noote_list,parent,false);
        return new MyViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textViewHeading.setText(list.get(position).getHeading());
        holder.textViewBody.setText(list.get(position).getBody());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewHeading;
        TextView textViewBody;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewHeading = itemView.findViewById(R.id.item_text);
            textViewBody = itemView.findViewById(R.id.tv_title);
        }

        @Override
        public void onClick(View view) {
            onNoteItemClick.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteItemClick {
        void onNoteClick(int pos);
    }
}