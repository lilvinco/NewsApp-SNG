package com.startng.newsapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.startng.newsapp.R;
import com.startng.newsapp.database.Notes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesVH> {

    public interface ListItemClickListener{
        void onListItemClick(int index);
    }

    final private ListItemClickListener mOnclickListener;
    private Context context;
    private List<Notes> mNotes; // Cached copy of notes

    //public constructor
    public NotesAdapter(Context context, ListItemClickListener mOnclickListener) {
        this.context = context;
        this.mOnclickListener = mOnclickListener;
    }
    //viewholder inner class
    class NotesVH extends RecyclerView.ViewHolder{
        MaterialCardView card;
        TextView txt1, txt2;
        View emptyView;
        //viewholder constructor
        NotesVH(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.note_card_view);
            txt1 = itemView.findViewById(R.id.note_des);
            txt2 = itemView.findViewById(R.id.recycler_view_note_time);
            emptyView = itemView.findViewById(R.id.empty_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    //long id = getItemId();
                    mOnclickListener.onListItemClick(index);
                }
            });
        }
    }

    @NonNull
    @Override
    public NotesAdapter.NotesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler_view_list_items, parent, false);
        return new NotesVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesVH holder, int position) {
        if (mNotes != null) {
            Notes current = mNotes.get(position);

            //getting info from Notes object
            DateFormat df = new SimpleDateFormat("EEEE, dd MMM yyyy hh:mm a", Locale.ENGLISH);
            String noteTime = df.format(current.getNoteTime());;
            String noteDesc = current.getNoteDesc();
            int noteColor = current.getNoteColor();

            //binding the views
            holder.txt1.setText(noteDesc);
            holder.txt2.setText(noteTime);
            if(noteColor != context.getResources().getIntArray(R.array.slider_colors)[0]) {
                holder.card.setStrokeColor(noteColor);
                holder.card.setCardBackgroundColor(noteColor);
            }else{
                holder.card.setStrokeColor(context.getResources().getColor(android.R.color.darker_gray));
                holder.card.setCardBackgroundColor(noteColor);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }

    public void setWords(List<Notes> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }

    public int getRoomItemId(int index){
        return mNotes.get(index).getId();
    }
}
