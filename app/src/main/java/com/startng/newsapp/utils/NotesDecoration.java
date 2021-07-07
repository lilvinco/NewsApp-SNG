package com.startng.newsapp.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesDecoration extends RecyclerView.ItemDecoration {
    private int spaceInPixels;
    public NotesDecoration(int spaceInPixels) {
        this.spaceInPixels = spaceInPixels;
    }

    @Override
    public void getItemOffsets(Rect outRect, @NonNull View view,
                               RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(spaceInPixels, spaceInPixels, spaceInPixels, spaceInPixels);
    }
}
