package com.example.resourcesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SampleActivity extends AppCompatActivity {

    private Button start, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sample );

        // assigning ID of startButton
        // to the object start
        start = (Button) findViewById( R.id.startButton );

        // assigning ID of stopButton
        // to the object stop
        stop = (Button) findViewById( R.id.stopButton );

        // declaring listeners for the
        // buttons to make them respond
        // correctly according to the process
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent( SampleActivity.this, NewService.class ) );
            }
        });
        stop.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent( SampleActivity.this, NewService.class ) );
            }
        } );
    }


}