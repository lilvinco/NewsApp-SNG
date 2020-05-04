package com.startng.newsapp.Helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ToastAndSnacksbar {

    public static void ToastMessage(Context context, View view, String message){
        hideKeyPad(context, view);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void onlyToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void SnackMessage(Context context, View view, String message){

        hideKeyPad(context, view);
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    private static void hideKeyPad(Context context, View view){
        // Hide keyPad
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
