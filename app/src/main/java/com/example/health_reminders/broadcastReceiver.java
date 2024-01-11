package com.example.health_reminders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

public class broadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
    mp = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
    mp.start();
    }
}
