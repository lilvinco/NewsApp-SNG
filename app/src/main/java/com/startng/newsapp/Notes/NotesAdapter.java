package com.startng.newsapp.Notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.startng.newsapp.Models.NotesModel;
import com.startng.newsapp.R;

public class NotesAdapter extends FirestoreRecyclerAdapter<NotesModel, NotesAdapter.NotesHolder> {

    Context context;
    private OnItemClickListener listener;

    public NotesAdapter(@NonNull FirestoreRecyclerOptions<NotesModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NotesHolder notesHolder, int i, @NonNull final NotesModel notesModel) {

        notesHolder.noteTitle.setText(notesModel.getTitle());
        notesHolder.noteContent.setText(notesModel.getContent());

    }

    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notes_model, viewGroup, false);

        return new NotesHolder(view);
    }

    public void deleteNote(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public class NotesHolder extends RecyclerView.ViewHolder {

        TextView noteTitle, noteContent;
        ImageView optionsMenu;


        public NotesHolder(@NonNull View itemView) {

            super(itemView);

            noteTitle = itemView.findViewById(R.id.note_title);
            noteContent = itemView.findViewById(R.id.note_content);
            optionsMenu = itemView.findViewById(R.id.menu_icon);

            // Clicks for the entire view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){

                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }

                }
            });

            // Clicks for the optionsMenu
            optionsMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onOptionMenuClick(getSnapshots().getSnapshot(position), position, v);
                    }

                }
            });

        }
    }

    public interface OnItemClickListener{

        void onItemClick(DocumentSnapshot documentSnapshot, int position);

        void onOptionMenuClick(DocumentSnapshot documentSnapshot, int position, View view); // View here will be used for popUp log_out_menu.(It is optional)
    }

    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;

    }

}



