package com.example.resourcesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.resourcesapp.R;
import com.example.resourcesapp.Services.MediaService;

public class MediaServiceActivity extends AppCompatActivity {
  private Button start;
  private  Button stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_service);
        start= findViewById(R.id.btn_start);
        stop = findViewById(R.id.btn_stop);

        Intent mediaServiceIntent= new Intent(MediaServiceActivity.this, MediaService.class);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(mediaServiceIntent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            stopService(mediaServiceIntent);
            }
        });
    }
}