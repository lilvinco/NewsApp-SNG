package com.startng.newsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyApp", "Alarm just fired");
//        NotificationScheduler.showNotification(context, MainActivity.class,
//                "You have 5 unwatched videos", "Watch them now?");

    }
}
