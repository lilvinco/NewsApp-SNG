package com.startng.newsapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Note> notes;

    NoteAdapter(Context context, List<Note> notes){
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        String title = notes.get(position).getTitle();
        String date = notes.get(position).getDate();
        String time = notes.get(position).getTime();
        long id = notes.get(position).getID();
        Log.d("Time","onBindViewHolder: Time ->" + time);
        holder.nTitle.setText(title);
        holder.nDate.setText(date);
        holder.nTime.setText(time);
        holder.nID.setText(String.valueOf(notes.get(position).getID()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nTitle,nDate,nTime,nID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nTitle = itemView.findViewById(R.id.nTitle);
            nDate = itemView.findViewById(R.id.nDate);
            nTime = itemView.findViewById(R.id.nTime);
            nID = itemView.findViewById(R.id.listId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(),Details.class);
                    i.putExtra("ID",notes.get(getAdapterPosition()).getID());
                    view.getContext().startActivity(i);
                }
            });
        }
    }
}
