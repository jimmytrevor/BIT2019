package com.example.resourcesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.InputStream;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView reader;
private String value;
    private VideoView viedo_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLanguage();
        setContentView(R.layout.activity_main);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String message=getResources().getString(R.string.message_key);
                Intent splashIntent = new Intent(MainActivity.this,LoginActivity.class);
                splashIntent.putExtra(getResources().getString(R.string.msg_variable),message);
                splashIntent.putExtra(getResources().getString(R.string.user_name),getResources().getString(R.string.passed_name));
                startActivity(splashIntent);
                finish();
            }
        },2000);

        String welcome_txt=getResources().getString(R.string.welcome_msg);
        viedo_view=findViewById(R.id.viedo_view);
        reader=findViewById(R.id.reader);

        MediaController mediaController= new MediaController(this);
        viedo_view.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video);
        mediaController.setAnchorView(viedo_view);
        viedo_view.setMediaController(mediaController);


        try {
            InputStream inputStream=getAssets().open("gui.txt");
            int size=inputStream.available();
            byte [] buffer=new byte[size];
            while (inputStream.read(buffer ) != -1){
                value=new String(buffer);
            }
            reader.setText(value);

        }catch (Exception e){

        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        if (itemId == R.id.action_english){
            setLocale("");
//            recreate();

        }
        else if (itemId == R.id.action_frech){
            setLocale("fr");
//            recreate();

        }
        else if (itemId == R.id.action_swahili){
            setLocale("sw-rUG");
//            recreate();
        }
        else{
            setLocale("");
//            recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLocale(String language){
        Locale locale=new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration=new Configuration();
        configuration.locale=locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("my_prefs", Context.MODE_PRIVATE).edit();
        editor.putString("language",language);
        editor.apply();
    }
    public  void checkLanguage(){
        SharedPreferences preferences=getSharedPreferences("my_prefs",Context.MODE_PRIVATE);
        String language=preferences.getString("language","");
        setLocale(language);
    }

    public void open_laguagae_dialog(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        String languaes[]={getResources().getString(R.string.english),getResources().getString(R.string.french),getResources().getString(R.string.swahili)};
        builder.setTitle("Select Language");
        builder.setSingleChoiceItems(languaes, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    setLocale("en");
                    recreate();
                }
                else if (which==1){
                    setLocale("fr");
                    recreate();
                }
                else if (which==2){
                    setLocale("sw-rUG");
                    recreate();
                }
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}