package com.startng.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
      private ArrayList<RecyclerItems> listItems;
      private OnItemClickListener recylerListener;
      private onLongItemClickListener longItemClickListener;




      public interface OnItemClickListener{

          void onItemClick(int position);
          void onDeleteClick(int position);

      }

      public interface onLongItemClickListener{
          public boolean  onLongClick(int position);
      }

      public void setOnItemClickListerner(OnItemClickListener listener){
          recylerListener = listener;
      }

      public void setonLongItemClickListener( onLongItemClickListener listener){
          longItemClickListener = listener;
      }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView imageView;
        private TextView textViewTitle;


        public MyViewHolder(View view , final OnItemClickListener listener, final onLongItemClickListener longListener) {
            super(view);
            imageView = view.findViewById(R.id.delete_icon);
            textViewTitle = view.findViewById(R.id.noteTitle);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(longListener != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION)
                        {
                            longListener.onLongClick(position);

                        }
                    }
                 return false;
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainAdapter(ArrayList<RecyclerItems> items) {
        listItems = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,parent,false);
       MyViewHolder vh = new MyViewHolder(v,recylerListener,longItemClickListener);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       RecyclerItems rt = listItems.get(position);

//       holder.imageView.setImageResource(rt.getDeleteImage());
       holder.textViewTitle.setText(rt.getNoteTitle());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listItems.size();
    }
}