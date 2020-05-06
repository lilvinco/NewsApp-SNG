package com.startng.newsapp.ViewModelFactory;

import android.app.Application;

import com.startng.newsapp.ViewModel.NoteViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NoteViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private Application mApplication;
    private String mParam;

    public NoteViewModelFactory(@NonNull Application application) {
        super(application);
        mApplication = application;
        //mParam = param;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new NoteViewModel(mApplication);
    }
}
