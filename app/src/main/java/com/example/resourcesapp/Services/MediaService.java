package com.example.resourcesapp.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

public class MediaService extends Service {

    private MediaPlayer mediaPlayer;
    @Override
    public  int onStartCommand(Intent intent, int flags, int startId){

        mediaPlayer = MediaPlayer.create(this,Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

     return  START_STICKY;
    }

    @Override
    public  void onDestroy(){
        mediaPlayer.stop();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
