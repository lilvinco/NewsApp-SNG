package com.startng.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.MyViewHolder> {
    private List<Note> note;
    private Context mContext;
   private NoteIntent mNoteIntent;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        TextView textView2;
        LinearLayout parent;

        public MyViewHolder(@NonNull View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            textView2 = v.findViewById(R.id.textView2);
            parent = v.findViewById(R.id.parent);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HeadlinesAdapter(Context mContext, NoteIntent mNoteIntent)
    {
        this.mContext = mContext;
        this.mNoteIntent = mNoteIntent;
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
        if (note != null ){
            // - get element from your Notebook class at this position
            Note current = note.get(position);
            // - replace the contents of the view with that element
            holder.textView.setText(current.getTitle());
            holder.textView2.setText(current.getContent());
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNoteIntent.updateIntent(current);
                }
            });
            holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(mContext, v);
                    popupMenu.inflate(R.menu.popup_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.the_delete:
                                    mNoteIntent.deleteIntent(current);
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

    public void setNoteBook(List<Note> noteBooks){
        note = noteBooks;
        notifyDataSetChanged();
    }

    // Return the size of your dataSet (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (note != null){
            return note.size();
        }else return 0;
    }
}