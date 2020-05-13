package com.startng.newsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.MyViewHolder> {
    private List<NoteBook> noteBook;
    private Context mContext;
    private UpdateInteraction update;


    // Provide a suitable constructor (depends on the kind of dataset)
    public HeadlinesAdapter(Context mContext, UpdateInteraction update) {
        this.mContext = mContext;
        this.update = update;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public HeadlinesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
        if (noteBook != null ){
            // - get element from your Notebook class at this position
            NoteBook current = noteBook.get(position);
            // - replace the contents of the view with that element
            holder.textView.setText(current.getTitle());
            holder.textView2.setText(current.getContent());
            //holder.textView3.setText(String.valueOf(current.getDate()));
            holder.parent1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update.onNewActivity(current);
                }
            });
            holder.parent1.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(mContext, v);
                    popupMenu.inflate(R.menu.popup_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.delete:
                                    update.deleteRow(current);
                                case R.id.delete_all:
                                    update.deleteRow2(current);
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                    return true;
                }
            });
        }
    }

    public void setNoteBook(List<NoteBook> noteBooks){
        noteBook = noteBooks;
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (noteBook != null){
            return noteBook.size();
        }else return 0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        TextView textView2;
        TextView textView3;
        ConstraintLayout parent1;

        public MyViewHolder(@NonNull View v) {
            super(v);
            textView = v.findViewById(R.id.titleText);
            textView2 = v.findViewById(R.id.contentText);
            textView3 = v.findViewById(R.id.dateText);
            parent1 = v.findViewById(R.id.parent);
        }
    }
}